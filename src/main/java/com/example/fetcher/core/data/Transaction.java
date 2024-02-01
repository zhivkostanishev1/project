package com.example.fetcher.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String transactionHash;
    private int transactionStatus;
    private String blockHash;
    private BigInteger blockNumber;
    private String fromAddress;
    private String toAddress; //nullable
    private String contractAddress; //nullable
    private int logsCount; //nullable
    @Column(length = 40000)
    private String input; //nullable
    @Column(name = "transaction_value")
    private BigInteger value;
}
