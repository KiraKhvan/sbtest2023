package com.example.sbtest.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "Card", indexes = {
        @Index(name = "idx_card_id", columnList = "id")
})
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client owner;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(name = "has_expiration_date_limit", nullable = false)
    private Boolean hasExpirationDateLimit = false;

    @Column(name = "expiration_date_limit")
    private Date expirationDateLimit;

    @Column(name = "activated", nullable = false)
    private Boolean activated = false;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "senderCard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionInfo> outgoingTransactionList;

    @OneToMany(mappedBy = "recipientCard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionInfo> incomingTransactionList;

    public void addBalance(BigDecimal amount) {
        BigDecimal newSenderBalance = getBalance().add(amount);
        setBalance(newSenderBalance);
    }

    public void minusBalance(BigDecimal amount) {
        BigDecimal newSenderBalance = getBalance().subtract(amount);
        setBalance(newSenderBalance);
    }
}
