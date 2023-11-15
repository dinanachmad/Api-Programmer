package com.testNutech.ApiProgrammer.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testNutech.ApiProgrammer.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Integer> {
	
	@Query("SELECT * FROM service WHERE service_code = :serviceCode")
	Service findByServiceCode(String serviceCode);
}
