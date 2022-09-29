package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Company {

	@Id
	private Long id;

	private String name;

	private String type;
}
