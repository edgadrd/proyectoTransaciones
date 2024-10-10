package com.nttdata.transaction.repository;


import com.nttdata.transaction.model.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
