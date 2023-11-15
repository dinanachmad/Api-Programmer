package com.testNutech.ApiProgrammer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpProfileResponse {
	private String email;
	private String first_name;
	private String last_name;
	private String profile_image;
}
