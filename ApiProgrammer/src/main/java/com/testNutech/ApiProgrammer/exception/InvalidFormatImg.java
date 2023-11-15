package com.testNutech.ApiProgrammer.exception;

public class InvalidFormatImg extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidFormatImg() {
		super("Format Image tidak sesuai");
	}
	
}
