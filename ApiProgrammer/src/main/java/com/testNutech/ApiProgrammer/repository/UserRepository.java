package com.testNutech.ApiProgrammer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testNutech.ApiProgrammer.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	public User findByEmail(String email);
}
