package com.testNutech.ApiProgrammer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.testNutech.ApiProgrammer.model.Service;
import com.testNutech.ApiProgrammer.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ServiceServiceImp implements ServiceService {
	
	@Autowired
	ServiceRepository serviceRepo;
	
	@Override
	public List<Service> getAllService() {
		// TODO Auto-generated method stub
		return (List<Service>) serviceRepo.findAll();
	}

	@Override
	public Service findByServiceCode(String serviceCode) {
		// TODO Auto-generated method stub
		return serviceRepo.findByServiceCode(serviceCode);
	}

}
