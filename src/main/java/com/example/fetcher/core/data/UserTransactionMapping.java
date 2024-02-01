package com.example.fetcher.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionMapping {
    @EmbeddedId
    private CompositeKey id;

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long userId;

    @Column(name = "transaction_hash", insertable=false, updatable=false)
    private String transactionHash;

}
