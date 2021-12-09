package com.sngular.omb.ombapi.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WithdrawRequest {
    @NotNull
    private Double amount;
}
