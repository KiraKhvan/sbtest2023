package com.example.sbtest.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccrualRequest {
    private String recipientCardId;
    private String amount;
}
