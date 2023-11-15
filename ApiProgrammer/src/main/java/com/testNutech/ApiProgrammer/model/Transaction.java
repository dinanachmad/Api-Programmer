package com.testNutech.ApiProgrammer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction")
public class Transaction {
	
	@Id
	private Integer id;
	
	private Integer user_id;
	private String invoice_number;
	private String service_code;
	private String service_name;
	private String transaction_type;
	private Integer total_amount;
	private String created_on;

}
