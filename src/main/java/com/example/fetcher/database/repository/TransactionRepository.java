package com.example.fetcher.database.repository;

import com.example.fetcher.core.data.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
