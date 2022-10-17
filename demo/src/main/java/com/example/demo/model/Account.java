package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.service.IsViewer;

import lombok.Data;

@Data
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	private String username;

	private String password;

	private boolean active;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	@IsViewer
	public boolean isActive() {
		return active;
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN') || #active == false")
	public void setActive(boolean active) {
		this.active = active;
	}

}
