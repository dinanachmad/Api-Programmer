package com.testNutech.ApiProgrammer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
public class Service {
	
	@Id
	private Integer id;
	
	@Column("service_code")
	private String serviceCode;
	
	@Column("service_name")
	private String serviceName;
	
	@Column("service_icon")
	private String serviceIcon;
	
	@Column("service_tarif")
	private Integer serviceTarif;
}
