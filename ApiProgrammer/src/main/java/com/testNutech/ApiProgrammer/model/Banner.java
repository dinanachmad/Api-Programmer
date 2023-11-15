package com.testNutech.ApiProgrammer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banner")
public class Banner {
	@Id
	private Integer id;
	
	@Column("banner_name")
	private String bannerName;
	
	@Column("banner_image")
	private String banner_image;
	
	private String description;
	
}
