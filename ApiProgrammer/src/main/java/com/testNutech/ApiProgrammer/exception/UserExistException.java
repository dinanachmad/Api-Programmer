package com.testNutech.ApiProgrammer.exception;

public class UserExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserExistException(String email) {
		super("Email " + email + " sudah terdaftar");
	}
}
