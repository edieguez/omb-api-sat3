package com.sngular.omb.ombapi.service;

import com.sngular.omb.ombapi.model.request.DepositRequest;
import com.sngular.omb.ombapi.model.response.DepositResponse;

public interface DepositService {
    DepositResponse makeDeposit(String accountId, DepositRequest depositRequest);
}
