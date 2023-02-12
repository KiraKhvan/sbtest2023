package com.example.sbtest;

import com.example.sbtest.entity.Card;
import com.example.sbtest.entity.Client;
import com.example.sbtest.entity.TransactionInfo;
import com.example.sbtest.exception.BadRequestException;
import com.example.sbtest.repository.CardRepository;
import com.example.sbtest.repository.ClientRepository;
import com.example.sbtest.repository.TransactionInfoRepository;
import com.example.sbtest.service.CardService;
import com.example.sbtest.service.ProcessingService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
@SpringBootTest
class SbtestApplicationTests {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionInfoRepository transactionInfoRepository;
    @Autowired
    CardService cardService;
    @Autowired
    ProcessingService processingService;

    @Test
    void test1() throws Exception {
        test();
    }

    @Test
    void stressTest() throws Exception {

        IntStream.range(0, 1000).forEach(i -> {
            Client client = new Client();
            client.setFirstName("Иван");
            client.setLastName("Иванович");
            client = clientRepository.save(client);
            UUID clientId = client.getId();
            IntStream.range(0, 100).forEach(j -> {
                try {
                    cardService.createCard(clientId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        System.out.println("Count: " + cardRepository.count());
        test();
    }

    public void test() throws Exception {
        //Создаем отправителя
        Client senderClient = new Client();
        senderClient.setFirstName("Иван");
        senderClient.setLastName("Иванович");
        senderClient = clientRepository.save(senderClient);

        //Создаем получателя
        Client recipientClient = new Client();
        recipientClient.setFirstName("Петр");
        recipientClient.setLastName("Иванович");
        recipientClient = clientRepository.save(recipientClient);

        //Создаем карту отправителя
        Card senderCard = cardService.createCard(senderClient.getId());
        UUID senderCardId = senderCard.getId();

        //Создаем карту получателя
        Card recipientCard = cardService.createCard(recipientClient.getId());
        UUID recipientCardId = recipientCard.getId();

        //Нельзя положить деньги на неактивированную карту
        assertThrows(
                BadRequestException.class,
                () -> processingService.accrualMoney(senderCardId, BigDecimal.TEN),
                "Карта не активирована"
        );

        //Активация карт
        cardService.issueCard(senderCardId);
        cardService.issueCard(recipientCardId);
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal zeroValue = new BigDecimal("0.00");

        //Нельзя перевести нуль)
        assertThrows(
                BadRequestException.class,
                () -> processingService.transferMoney(senderCardId, recipientCardId, zeroValue),
                "Переводимая сумма задана некорректно"
        );

        //Кладем на карту отправителя сто рублей
        processingService.accrualMoney(senderCardId, amount);
        BigDecimal biggerAmount = new BigDecimal("1000.00");

        //Нельзя перевести с карты отправителя больше денег чем там есть
        assertThrows(
                BadRequestException.class,
                () -> processingService.transferMoney(senderCardId, recipientCardId, biggerAmount),
                "Переводимая сумма меньше баланса карты отправителя"
        );

        //Проверяем, что транзакция откатилась
        senderCard = cardRepository.findById(senderCardId).get();
        assertEquals(senderCard.getBalance(), amount);
        recipientCard = cardRepository.findById(recipientCardId).get();
        assertEquals(recipientCard.getBalance(), zeroValue);
        TransactionInfo transactionInfo = transactionInfoRepository
                .findByRecipientCardId(recipientCard.getId())
                .orElse(null);
        //транзакция не создана
        assertNull(transactionInfo);

        //Переводим с карты отправителя все деньги
        processingService.transferMoney(senderCardId, recipientCardId, amount);

        //Проверяем что баланс карты изменился
        recipientCard = cardRepository.findById(recipientCardId).get();
        assertEquals(recipientCard.getBalance(), amount);
        senderCard = cardRepository.findById(senderCardId).get();
        assertEquals(senderCard.getBalance(), zeroValue);

        //транзакция создана
        transactionInfo = transactionInfoRepository.findByRecipientCardId(recipientCard.getId()).orElse(null);
        assertNotNull(transactionInfo);
        assertEquals(transactionInfo.getAmount(), amount);
        assertEquals(transactionInfo.getRecipientCard().getId(), recipientCardId);
        assertEquals(transactionInfo.getSenderCard().getId(), senderCardId);
    }
}
