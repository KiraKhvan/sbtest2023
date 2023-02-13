package com.example.sbtest.service;

import com.example.sbtest.entity.Card;
import com.example.sbtest.entity.TransactionInfo;

import java.math.BigDecimal;

public interface TransactionInfoService {

    TransactionInfo createTransactionInfo(
            Card senderCard,
            Card recipientCard,
            BigDecimal amount
    );
}
