package com.sngular.omb.ombapi.service.impl;

import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.repository.AccountsRepository;
import com.sngular.omb.ombapi.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsServiceImpl implements AccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    @Override
    public List<Account> getAccounts() {
        return accountsRepository.findAll();
    }

    @Override
    public Optional<Account> getAccount(String id) {
        return accountsRepository.findById(id);
    }

    @Override
    public Account upsertAccount(Account account) {
        return accountsRepository.save(account);
    }
}
