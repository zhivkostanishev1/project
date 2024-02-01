package com.example.fetcher.core.service;

import com.example.fetcher.core.data.Transaction;
import com.example.fetcher.core.data.User;

import java.io.IOException;
import java.util.List;

public interface TransactionService {

    public List<Transaction> getTransactionsByHashCode(List<String> transactionHashes);

    public List<Transaction> getAllSavedTransactions();

    public List<Transaction> getAllTransactionsByRlp(String rlp);

    public void saveTransactionsForUser(List<Transaction> transactions, User user);

    public List<Transaction> getAllSavedTransactionsForUser(User user);
}
