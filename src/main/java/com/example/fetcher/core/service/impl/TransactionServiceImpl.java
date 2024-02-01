package com.example.fetcher.core.service.impl;

import com.example.fetcher.core.data.CompositeKey;
import com.example.fetcher.core.data.Transaction;
import com.example.fetcher.core.data.User;
import com.example.fetcher.core.data.UserTransactionMapping;
import com.example.fetcher.core.exception.CommunicationException;
import com.example.fetcher.core.service.TransactionService;
import com.example.fetcher.core.service.gateway.EthClient;
import com.example.fetcher.database.repository.TransactionRepository;
import com.example.fetcher.database.repository.UserTransactionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.fetcher.core.util.RlpUtil.decodeRlpToListOfHashes;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserTransactionRepository userTransactionRepository;

    @Autowired
    EthClient ethClient;

    @Override
    public List<Transaction> getTransactionsByHashCode(List<String> transactionHashes) {
        Map<String, Transaction> transactionsMap = getMapWithEmptyTransactions(transactionHashes);

        List<Transaction> transactionsFetchedFromDb = transactionRepository.findAllById(transactionHashes);

        transactionsMap.putAll(getMapWithTransactionsFetchedFromDb(transactionsFetchedFromDb, transactionsMap));

        List<String> hashesToGetFromNet = transactionsMap.entrySet().stream()
                .filter(transaction -> transaction.getValue().getTransactionHash() == null)
                .map(Map.Entry::getKey)
                .toList();

        if (!hashesToGetFromNet.isEmpty()) {
            Map<String, Transaction> transactionGotFromTheNet = getTransactionsFromTheNet(hashesToGetFromNet);

            transactionsMap.putAll(transactionGotFromTheNet);
        }



        return new ArrayList<>(transactionsMap.values());
    }

    @Override
    public List<Transaction> getAllSavedTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionsByRlp(String rlp) {
        List<String> transactionHashes = decodeRlpToListOfHashes(rlp);

        return getTransactionsByHashCode(transactionHashes);
    }

    @Override
    public void saveTransactionsForUser(List<Transaction> transactions, User user) {
        for (Transaction transaction : transactions) {
            UserTransactionMapping mapping = UserTransactionMapping.builder()
                    .id(CompositeKey.builder()
                            .transactionHash(transaction.getTransactionHash())
                            .userId(user.getId())
                            .build())
                    .build();

            userTransactionRepository.save(mapping);
        }
    }

    @Override
    public List<Transaction> getAllSavedTransactionsForUser(User user) {
        List<UserTransactionMapping> transactionsMappings = userTransactionRepository.findByUserId(user.getId());
        List<String> transactionHashes = transactionsMappings
                .stream()
                .map(UserTransactionMapping::getTransactionHash)
                .toList();
        return transactionRepository.findAllById(transactionHashes);
    }

    @NotNull
    private Map<String, Transaction> getMapWithEmptyTransactions(List<String> transactionHashes) {
        return transactionHashes
                .stream()
                .collect(Collectors.toMap(key -> key, value -> Transaction.builder().build()));
    }

    @NotNull
    private Map<String, Transaction> getMapWithTransactionsFetchedFromDb(List<Transaction> transactionsFetchedFromDb,
                                                                                Map<String, Transaction> transactionsMap) {
        return transactionsFetchedFromDb.stream()
                .filter(transaction -> transactionsMap.containsKey(transaction.getTransactionHash()))
                .collect(Collectors.toMap(Transaction::getTransactionHash, transaction -> transaction));
    }

    private Map<String, Transaction> getTransactionsFromTheNet(List<String> hashesToGetFromNet) {
        Map<String, Transaction> transactionsGotFromTheNet;
        try {
            transactionsGotFromTheNet = ethClient.getTransactionsFromTheNet(hashesToGetFromNet);
        } catch(Exception e) {
            throw new CommunicationException(e.getMessage());
        }

        transactionRepository.saveAll(transactionsGotFromTheNet.values());

        return transactionsGotFromTheNet;
    }
}
