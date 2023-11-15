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
@Table(name = "history_transaction")
public class HistoryTransaction {
	@Id
	private Integer id;
	
	private Integer user_id;
	private String invoice_number;
	private String transaction_type;
	private String description;
	private Integer total_amount;
	private String created_on;
}
