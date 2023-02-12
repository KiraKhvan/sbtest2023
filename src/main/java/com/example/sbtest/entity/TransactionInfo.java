package com.example.sbtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Table(name = "TransactionInfo", indexes = {
        @Index(name = "idx_sender_card_id", columnList = "sender_card_id"),
        @Index(name = "idx_recipient_card_id", columnList = "recipient_card_id")
})
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class TransactionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_card_id", nullable = false)
    private Card senderCard;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_card_id", nullable = false)
    private Card recipientCard;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Version
    private Integer version;
}
