package com.testNutech.ApiProgrammer.exception;

public class BalanceKurang extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public BalanceKurang() {
		super("Service ataus Layanan tidak ditemukan atau Balance Kurang, segera Top Up");
	}
}
