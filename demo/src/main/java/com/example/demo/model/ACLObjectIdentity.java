package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "acl_object_identity")
public class ACLObjectIdentity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "object_id_class")
	private ACLClass aclClass;

	@ManyToOne
	@JoinColumn(name = "owner_sid")
	private ACLSID aclsid;

	@ManyToOne
	@JoinColumn(name = "parent_object")
	ACLObjectIdentity parentObjectIdentity;

	@Column(name = "object_id_identity")
	private Long objectIdIdentity;

	@Column(name = "entries_inheriting")
	private Integer entriesInheriting;// 0 , 1
}
