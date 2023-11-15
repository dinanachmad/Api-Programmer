package com.testNutech.ApiProgrammer.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class CekFormatEmail {
	private static final String PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	
	public boolean cekFormat(String email) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()) {
			return true;
		}else {
			return false;
		}		  
	}
}
