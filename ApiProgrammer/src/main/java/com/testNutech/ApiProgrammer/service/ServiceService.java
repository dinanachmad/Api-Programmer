package com.testNutech.ApiProgrammer.service;

import java.util.List;

import com.testNutech.ApiProgrammer.model.Service;

public interface ServiceService {
	List<Service> getAllService();
	Service findByServiceCode(String serviceCode);
}
