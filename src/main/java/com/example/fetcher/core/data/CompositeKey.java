package com.example.fetcher.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositeKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "transaction_hash")
    private String transactionHash;
}
