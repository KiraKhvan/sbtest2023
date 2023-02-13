package com.example.sbtest.rest.controller;

import com.example.sbtest.rest.dto.AccrualRequest;
import com.example.sbtest.rest.dto.TransferRequest;
import com.example.sbtest.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/processing")
public class ProcessingController {

    @Autowired
    ProcessingService processingService;

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest transferRequest) {
        processingService.transferMoney(
                transferRequest.getSenderCardId(),
                transferRequest.getRecipientCardId(),
                transferRequest.getAmount()
        );
    }

    @PostMapping("/accrual")
    public void accrual(@RequestBody AccrualRequest accrualRequest) {
        processingService.accrualMoney(
                UUID.fromString(accrualRequest.getRecipientCardId()),
                new BigDecimal(accrualRequest.getAmount())
        );
    }
}
