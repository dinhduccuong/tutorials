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
@Table(name = "acl_entry")
public class ACLEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "acl_object_identity")
	private ACLObjectIdentity aclObjectIdentity;

	@Column(name = "ace_order")
	private Integer aceOrder;

	@ManyToOne
	@JoinColumn(name = "sid")
	private ACLSID sid;

	private Integer mask;

	private Integer granting;

	@Column(name = "audit_success")
	private Integer auditSuccess;

	@Column(name = "audit_failure")
	private Integer auditFailure;
}
