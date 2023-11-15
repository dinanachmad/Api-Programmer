package com.testNutech.ApiProgrammer.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testNutech.ApiProgrammer.model.HistoryTransaction;

@Repository
public interface HistoryTransactionRepository extends CrudRepository<HistoryTransaction, Integer>{
	
	@Query("SELECT * FROM history_transaction WHERE user_id = :userId LIMIT :limit OFFSET :offset")
	List<HistoryTransaction> listHistoryTransWithSpecificUserId(Integer userId, Integer limit, Integer offset);
	
}
