package com.example.sbtest.rest.controller;

import com.example.sbtest.rest.dto.TransferDto;
import com.example.sbtest.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processing")
public class ProcessingController {

    @Autowired
    ProcessingService processingService;

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferDto transferDto) throws Exception {
        processingService.transferMoney(
                transferDto.getSenderCardId(),
                transferDto.getRecipientCardId(),
                transferDto.getAmount()
        );
    }
}
