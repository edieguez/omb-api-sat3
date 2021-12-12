package com.sngular.omb.ombapi.service.impl;

import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.model.response.AccountType;
import com.sngular.omb.ombapi.repository.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountsServiceImplTest {
    @InjectMocks
    private AccountsServiceImpl accountsService;

    @Mock
    private AccountsRepository accountsRepository;

    @Test
    void getAccounts() {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        when(accountsRepository.findAll()).thenReturn(List.of(account));

        List<Account> accounts = accountsService.getAccounts();

        assertThat(accounts).isNotNull();
        assertThat(accounts.size()).isEqualTo(1);
    }

    @Test
    void getAccount() {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        when(accountsRepository.findById("1")).thenReturn(Optional.of(account));

        Optional<Account> accountOptional = accountsService.getAccount("1");

        assertThat(accountOptional.isPresent()).isTrue();
        assertThat(accountOptional.get()).isEqualTo(account);
    }

    @Test
    void upsertAccount_insertAccount() {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        when(accountsRepository.save(account)).thenReturn(account);

        Account accountSaved = accountsService.upsertAccount(account);

        assertThat(accountSaved).isEqualTo(account);
    }

    @Test
    void upsertAccount_updateAccount() {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        when(accountsRepository.save(account)).thenReturn(account);

        account.setName("Jane Doe");

        Account accountSaved = accountsService.upsertAccount(account);

        assertThat(accountSaved).isEqualTo(account);
        assertThat(accountSaved.getName()).isEqualTo("Jane Doe");
    }
}