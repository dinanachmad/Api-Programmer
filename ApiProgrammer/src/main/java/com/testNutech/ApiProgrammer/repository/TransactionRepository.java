package com.testNutech.ApiProgrammer.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testNutech.ApiProgrammer.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{
	
	@Query("SELECT * FROM transaction WHERE user_id = :userId")
	Transaction findTransId(Integer userId);
	
	@Query("SELECT invoice_number FROM transaction WHERE user_id = :userId")
	String findInvoice(Integer userId);
	
	@Query("SELECT id FROM transaction WHERE user_id = :userId")
	Integer findId(Integer userId);
}
