package com.sngular.omb.ombapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DepositResponse {
    private String accountId;
    private Double currentBalance;
}
