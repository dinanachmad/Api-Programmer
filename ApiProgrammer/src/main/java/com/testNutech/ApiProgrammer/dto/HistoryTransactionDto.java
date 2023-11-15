package com.testNutech.ApiProgrammer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryTransactionDto {

	private String invoice_number;
	private String transaction_type;
	private String description;
	private Integer total_amount;
	private String created_on;
}
