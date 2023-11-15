package com.testNutech.ApiProgrammer;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@OpenAPIDefinition(
		info = @Info(
				title="Test PT Nutech Integrasi",
				description="<h3>Pembuatan project Rest Api</h3>",
				contact = @Contact(
						name = "Dinan Achmad Fauzan",
						email = "dinan.achmadf22@gmail.com"))
	)

@SpringBootApplication
@SecurityScheme(name = "Tokenbearer", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP)
public class ApiProgrammerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiProgrammerApplication.class, args);
	}

}
