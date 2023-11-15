package com.testNutech.ApiProgrammer.exception;

public class UserInvalidPassword extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UserInvalidPassword() {
		super("Username atau password salah");
	}
}
