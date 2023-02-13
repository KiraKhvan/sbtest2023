package com.example.sbtest.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class IssueCardRequest {
    UUID cardId;
}
