package com.example.sbtest.service;

import com.example.sbtest.CardBalanceOperation;
import com.example.sbtest.entity.Card;
import com.example.sbtest.entity.Client;
import com.example.sbtest.exception.BadRequestException;
import com.example.sbtest.exception.NotFoundException;
import com.example.sbtest.repository.CardRepository;
import com.example.sbtest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceBean implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Card createCard(UUID clientId) throws Exception {
        Optional<Client> ownerObj = clientRepository.findById(clientId);
        Client owner = ownerObj.orElseThrow(
                () -> new NotFoundException(Client.class, clientId)
        );
        Card card = new Card();
        card.setOwner(owner);
        card.setBalance(BigDecimal.ZERO);
        cardRepository.save(card);
        return card;
    }

    @Override
    public boolean issueCard(UUID cardId) throws Exception {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        Card card = cardOptional.orElseThrow(
                () -> new NotFoundException(Card.class, cardId)
        );
        card.setActivated(true);
        cardRepository.save(card);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BadRequestException.class, NotFoundException.class})
    public Card loadAndCheck(UUID cardId) throws Exception {
        if (cardId == null) {
            throw new BadRequestException("Card ID is empty.");
        }
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        Card card = cardOptional.orElseThrow(
                () -> new NotFoundException(Card.class, cardId)
        );
        if (!card.getActivated()) {
            throw new BadRequestException("Карта не активирована");
        }
        return card;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BadRequestException.class)
    @Override
    public void editCardBalance(Card card, BigDecimal amount, CardBalanceOperation operation) throws Exception {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Сумма задана некорректно.");
        }
        switch (operation) {
            case ADD -> {
                card.addBalance(amount);
            }
            case MINUS -> {
                if (card.getBalance().compareTo(amount) < 0) {
                    throw new BadRequestException("Снимаемая сумма меньше баланса карты.");
                }
                card.minusBalance(amount);
            }
        }
        cardRepository.save(card);
    }
}
