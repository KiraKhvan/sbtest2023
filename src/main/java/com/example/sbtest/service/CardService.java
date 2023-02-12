package com.example.sbtest.service;

import com.example.sbtest.CardBalanceOperation;
import com.example.sbtest.entity.Card;

import java.math.BigDecimal;
import java.util.UUID;

public interface CardService {

    Card createCard(UUID clientId) throws Exception;

    boolean issueCard(UUID cardId) throws Exception;

    Card loadAndCheck(UUID cardId) throws Exception;

    void editCardBalance(Card card, BigDecimal amount, CardBalanceOperation operation) throws Exception;
}
