package com.example.sbtest.service;

import com.example.sbtest.entity.Card;
import com.example.sbtest.entity.TransactionInfo;
import com.example.sbtest.repository.TransactionInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;

@Service
public class TransactionInfoServiceBean implements TransactionInfoService {

    @Autowired
    TransactionInfoRepository transactionInfoRepository;

    @Override
    @Transactional
    public TransactionInfo createTransactionInfo(
            Card senderCard,
            Card recipientCard,
            BigDecimal amount
    ) {
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setSenderCard(senderCard);
        transactionInfo.setRecipientCard(recipientCard);
        transactionInfo.setAmount(amount);
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new Date(date.getTime());
        transactionInfo.setDate(sqlDate);
        transactionInfo = transactionInfoRepository.save(transactionInfo);
        return transactionInfo;
    }
}
