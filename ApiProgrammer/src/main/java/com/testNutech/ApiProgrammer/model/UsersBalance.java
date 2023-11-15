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
@Table(name = "users_balance")
public class UsersBalance {
	@Id
	private Integer id;
	
	@Column("user_id")
	private Integer userId;
	
	private Integer balance;
}
