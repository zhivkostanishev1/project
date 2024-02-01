package com.example.fetcher.database.repository;

import com.example.fetcher.FetcherApplication;
import com.example.fetcher.core.data.Transaction;
import com.example.fetcher.database.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FetcherApplication.class)
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void saveAndFindById_sampleData_resultAsExpected() {
        String transactionHash = "someTransactionHash";

        Transaction transaction = transactionRepository
                .save(Transaction.builder().transactionHash(transactionHash).build());
        Transaction transactionFound = transactionRepository
                .findById(transaction.getTransactionHash()).orElse(null);

        assertNotNull(transactionFound);
        assertEquals(transaction.getTransactionHash(), transactionFound.getTransactionHash());
    }

    @Test
    public void saveAllAndFindALlById_sampleData_resultAsExpected() {
        Transaction transaction1 = Transaction.builder().transactionHash("someTransactionHash").build();
        Transaction transaction2 = Transaction.builder().transactionHash("anotherTransactionHash").build();
        List<Transaction> transactions = List.of(transaction1,transaction2);

        List<Transaction> transactionsSaved = transactionRepository.saveAll(transactions);
        List<Transaction> transactionsFound = transactionRepository
                .findAllById(transactions.stream().map(Transaction::getTransactionHash).collect(Collectors.toList()));

        assertNotNull(transactionsFound);
        assertNotNull(transactionsSaved);
        assertEquals(transactionsSaved.size(), transactionsFound.size());
        assertTrue(transactionsFound.stream().map(Transaction::getTransactionHash).toList().contains(transaction2.getTransactionHash()));
        assertTrue(transactionsFound.stream().map(Transaction::getTransactionHash).toList().contains(transaction1.getTransactionHash()));
    }
}
