package com.example.fetcher.database.repository;

import com.example.fetcher.core.data.CompositeKey;
import com.example.fetcher.core.data.UserTransactionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserTransactionRepository extends JpaRepository<UserTransactionMapping, CompositeKey> {

    List<UserTransactionMapping> findByUserId(Long userId);

}
