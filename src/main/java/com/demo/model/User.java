package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column
	private String email;
	@Column
	private Long phoneNo;
	@Column(nullable = false)
	private String role;
}
