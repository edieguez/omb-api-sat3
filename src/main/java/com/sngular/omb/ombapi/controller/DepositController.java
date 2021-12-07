package com.sngular.omb.ombapi.controller;

import com.sngular.omb.ombapi.model.request.DepositRequest;
import com.sngular.omb.ombapi.model.response.DepositResponse;
import com.sngular.omb.ombapi.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @PostMapping("/accounts/{accountId}/deposit")
    public DepositResponse postDeposit(@PathVariable String accountId, @RequestBody DepositRequest depositRequest) {
        return depositService.makeDeposit(accountId,depositRequest);
    }
}
