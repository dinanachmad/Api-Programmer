package com.testNutech.ApiProgrammer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testNutech.ApiProgrammer.model.UsersBalance;
import com.testNutech.ApiProgrammer.repository.UsersBalanceRepository;

@Service
public class UsersBalnceServiceImp implements UsersBalanceService{
	
	@Autowired
	UsersBalanceRepository userBalanceRepo;
	
	@Override
	public UsersBalance getUsersBalance(Integer userId) {
		// TODO Auto-generated method stub
		return userBalanceRepo.findBalance(userId);
	}
	
}
