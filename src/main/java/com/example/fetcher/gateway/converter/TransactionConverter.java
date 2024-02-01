package com.example.fetcher.gateway.converter;

import com.example.fetcher.core.data.Transaction;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.util.Objects.nonNull;

@Component
public class TransactionConverter {

    public Transaction from(org.web3j.protocol.core.methods.response.Transaction web3jTransaction, TransactionReceipt receipt) {
        return Transaction.builder()
                .transactionHash(web3jTransaction.getHash())
                .fromAddress(web3jTransaction.getFrom())
                .toAddress(web3jTransaction.getTo())
                .input(web3jTransaction.getInput())
                .blockHash(web3jTransaction.getBlockHash())
                .blockNumber(web3jTransaction.getBlockNumber())
                .value(web3jTransaction.getValue())
                .contractAddress(getContractAddress(receipt))
                .transactionStatus(getTransactionStatus(receipt))
                .logsCount(getLogsCount(receipt))
                .build();
    }

    private String getContractAddress(TransactionReceipt receipt) {
        if (nonNull(receipt)) {
            return isNotEmpty(receipt.getContractAddress()) ? receipt.getContractAddress(): "";
        }
        return "";
    }

    private int getLogsCount(TransactionReceipt receipt) {
        if (nonNull(receipt)) {
            return receipt.getLogs().size();
        }
        return 0;
    }

    private int getTransactionStatus(TransactionReceipt receipt) {
        if (nonNull(receipt)) {
            return Integer.decode(receipt.getStatus());
        }
        return 0;
    }

}
