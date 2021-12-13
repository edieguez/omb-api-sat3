package com.sngular.omb.ombapi.service.impl;

import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.model.request.DepositRequest;
import com.sngular.omb.ombapi.model.response.DepositResponse;
import com.sngular.omb.ombapi.repository.AccountsRepository;
import com.sngular.omb.ombapi.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepositServiceImpl implements DepositService {

    @Autowired
    AccountsRepository accountsRepository;

    @Override
    public DepositResponse makeDeposit(String accountId, DepositRequest depositRequest) {
        Optional<Account> accountOptional = accountsRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setCurrentBalance(account.getCurrentBalance() + depositRequest.getAmount());
            accountsRepository.save(account);
            return new DepositResponse(account.getId(), account.getCurrentBalance());
        } else{
            return new DepositResponse("Account not found", 0.0);
        }
    }

}
