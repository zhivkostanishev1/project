package com.example.fetcher.gateway;

import com.example.fetcher.core.data.Transaction;
import com.example.fetcher.gateway.converter.TransactionConverter;
import com.example.fetcher.core.service.gateway.EthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class Web3jClient implements EthClient {

    @Value("${eth.node.url}")
    private String ehNodeUrl;
    private final String apiKey = "IpZBAfQlClBYtOlik2ySmwMnhv_aiu-H";

    @Autowired
    TransactionConverter transactionConverter;
    @Override
    public Map<String, Transaction> getTransactionsFromTheNet(List<String> hashesToGet) throws IOException {
        Web3j web3j = getWeb3jService();
        Map<String, Transaction> transactionsMap = new HashMap<>();

        for (String hash : hashesToGet) {
            Optional<org.web3j.protocol.core.methods.response.Transaction> optionalTransaction =
                    web3j.ethGetTransactionByHash(hash).send().getTransaction();
            Optional<TransactionReceipt> receipt = web3j.ethGetTransactionReceipt(hash).send().getTransactionReceipt();
            optionalTransaction.ifPresent(value -> {
                Transaction transaction = transactionConverter.from(value, receipt.orElse(null));
                transactionsMap.put(hash, transaction);
            });
        }

        return transactionsMap;
    }

    private Web3j getWeb3jService() {
        HttpService service = new HttpService(ehNodeUrl);
        service.addHeader("API", apiKey);
        return Web3j.build(service);
    }
}
