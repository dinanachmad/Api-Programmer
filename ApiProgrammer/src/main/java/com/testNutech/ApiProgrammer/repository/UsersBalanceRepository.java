package com.testNutech.ApiProgrammer.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testNutech.ApiProgrammer.model.UsersBalance;

@Repository
public interface UsersBalanceRepository extends CrudRepository<UsersBalance, Integer>{
	
	@Query(value = "SELECT * FROM users_balance WHERE user_id = :userId")
	UsersBalance findBalance(Integer userId);
}
