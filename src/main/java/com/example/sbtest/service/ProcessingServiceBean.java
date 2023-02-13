package com.example.sbtest.service;

import com.example.sbtest.CardBalanceOperation;
import com.example.sbtest.entity.Card;
import com.example.sbtest.exception.BadRequestException;
import com.example.sbtest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProcessingServiceBean implements ProcessingService {

    @Autowired
    CardService cardService;
    @Autowired
    TransactionInfoService transactionInfoService;

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {BadRequestException.class, NotFoundException.class}
    )
    @Override
    public void transferMoney(
            UUID senderCardId,
            UUID recipientCardId,
            BigDecimal amount
    ) {
        Card senderCard = cardService.loadAndCheck(senderCardId);
        Card recipientCard = cardService.loadAndCheck(recipientCardId);
        cardService.editCardBalance(recipientCard, amount, CardBalanceOperation.ADD);
        cardService.editCardBalance(senderCard, amount, CardBalanceOperation.MINUS);
        transactionInfoService.createTransactionInfo(senderCard, recipientCard, amount);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {BadRequestException.class, NotFoundException.class}
    )
    @Override
    public void accrualMoney(UUID recipientCardId, BigDecimal amount) {
        Card recipientCard = cardService.loadAndCheck(recipientCardId);
        cardService.editCardBalance(recipientCard, amount, CardBalanceOperation.ADD);
    }
}
