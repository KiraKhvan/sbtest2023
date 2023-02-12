package com.example.sbtest.service;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProcessingService {

    void transferMoney(
            @Nonnull UUID senderCardId,
            @Nonnull UUID recipientCardId,
            @Nonnull BigDecimal amount
    ) throws Exception;

    void accrualMoney(UUID recipientCardId, BigDecimal amount) throws Exception;
}
