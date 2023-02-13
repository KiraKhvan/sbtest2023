package com.example.sbtest.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class TransferRequest {

    private UUID senderCardId;
    private UUID recipientCardId;
    private BigDecimal amount;
}
