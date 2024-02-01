package com.example.fetcher.core.service.gateway;

import com.example.fetcher.core.data.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EthClient {

    public Map<String, Transaction> getTransactionsFromTheNet(List<String> hashesToGet) throws IOException;

}
