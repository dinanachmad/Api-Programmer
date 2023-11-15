package com.testNutech.ApiProgrammer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User {
	@Id
	private Integer id;
	
	private String email;
	
	@Column("first_name")
	private String firstName;
	
	@Column("last_name")
	private String lastName;

	private String password;
	
	@Column("profile_img")
	private String profileImg;
	
}
