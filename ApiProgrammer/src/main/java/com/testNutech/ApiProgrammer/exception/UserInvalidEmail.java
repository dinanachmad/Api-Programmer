package com.testNutech.ApiProgrammer.exception;

public class UserInvalidEmail extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserInvalidEmail() {
		super("Paramter email tidak sesuai format");
	}
}
