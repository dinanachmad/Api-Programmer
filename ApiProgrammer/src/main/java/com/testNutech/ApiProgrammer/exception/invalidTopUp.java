package com.testNutech.ApiProgrammer.exception;

public class invalidTopUp extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public invalidTopUp() {
		super("Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0");
	}
	
}
