package com.example.sbtest.rest.controller;

import com.example.sbtest.rest.dto.CreateCardRequest;
import com.example.sbtest.rest.dto.IssueCardRequest;
import com.example.sbtest.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/create")
    public void create(@RequestBody CreateCardRequest createCardRequest) {
        cardService.createCard(createCardRequest.getClientId());
    }

    @PostMapping("/issue")
    public void issue(@RequestBody IssueCardRequest issueCardRequest) {
        cardService.issueCard(issueCardRequest.getCardId());
    }
}
