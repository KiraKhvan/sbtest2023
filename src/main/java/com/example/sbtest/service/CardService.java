package com.example.sbtest.service;

import com.example.sbtest.CardBalanceOperation;
import com.example.sbtest.entity.Card;

import java.math.BigDecimal;
import java.util.UUID;

public interface CardService {

    Card createCard(UUID clientId);

    boolean issueCard(UUID cardId);

    Card loadAndCheck(UUID cardId);

    void editCardBalance(Card card, BigDecimal amount, CardBalanceOperation operation);
}
