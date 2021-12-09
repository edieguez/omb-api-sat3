package com.sngular.omb.ombapi.service;

import com.sngular.omb.ombapi.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsService {
    List<Account> getAccounts();

    Account upsertAccount(Account account);

    Optional<Account> getAccount(String id);
}
