package com.example.fetcher.web.rest.controller;

import com.example.fetcher.core.data.Transaction;
import com.example.fetcher.core.data.User;
import com.example.fetcher.core.service.TransactionService;
import com.example.fetcher.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("lime")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/eth")
    public ResponseEntity<Map<String, List<Transaction>>> getTransactionsByHashCode(
            @RequestParam List<String> transactionHashes,
            @RequestHeader(name = "AUTH_TOKEN", required = false) String tokenHeader) {
        User user = userService.getUser(tokenHeader);

        List<Transaction> transactions = transactionService.getTransactionsByHashCode(transactionHashes);

        if (user != null) {
            transactionService.saveTransactionsForUser(transactions, user);
        }

        return new ResponseEntity<>(Map.of("transactions", transactions), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<Transaction>>> getAllSavedTransactions() {
        List<Transaction> transactions = transactionService.getAllSavedTransactions();

        return new ResponseEntity<>(Map.of("transactions", transactions), HttpStatus.OK);
    }

    @GetMapping("/eth/{transaction}")
    public ResponseEntity<Map<String, List<Transaction>>> getTransactionByPathVariable(
            @PathVariable String transaction,
            @RequestHeader(name = "AUTH_TOKEN", required = false) String tokenHeader) {
        User user = userService.getUser(tokenHeader);

        List<Transaction> transactions = transactionService.getAllTransactionsByRlp(transaction);

        if (user != null) {
            transactionService.saveTransactionsForUser(transactions, user);
        }

        return new ResponseEntity<>(Map.of("transactions", transactions), HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<Map<String, List<Transaction>>> getAllSavedTransactionsForUser(
            @RequestHeader(name = "AUTH_TOKEN") String tokenHeader) {

        List<Transaction> transactions = transactionService.getAllSavedTransactionsForUser(userService.getUser(tokenHeader));

        return new ResponseEntity<>(Map.of("transactions", transactions), HttpStatus.OK);
    }

}
