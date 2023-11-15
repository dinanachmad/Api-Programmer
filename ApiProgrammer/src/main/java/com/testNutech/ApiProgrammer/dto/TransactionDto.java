package com.testNutech.ApiProgrammer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
	private Integer user_id;
	private String invoice_number;
	private String service_code;
	private String service_name;
	private String transaction_type;
	private Integer total_amount;
	private String created_on;
}
