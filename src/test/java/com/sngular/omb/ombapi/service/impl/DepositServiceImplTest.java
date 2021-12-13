package com.sngular.omb.ombapi.service.impl;

import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.model.request.DepositRequest;
import com.sngular.omb.ombapi.model.response.AccountType;
import com.sngular.omb.ombapi.model.response.DepositResponse;
import com.sngular.omb.ombapi.repository.AccountsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DepositServiceImplTest {
    @InjectMocks
    private DepositServiceImpl depositService;

    @Mock
    private AccountsRepository accountsRepository;

    @Test
    void makeDeposit() {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        Account updatedAccount = new Account("1", "John Doe", "042", "12345", "01-01-1970", 2_000D, AccountType.SAVINGS);

        when(accountsRepository.findById("1")).thenReturn(Optional.of(account));
        when(accountsRepository.save(account)).thenReturn(updatedAccount);

        DepositResponse depositResponse = depositService.makeDeposit("1", new DepositRequest(1_000D));

        verify(accountsRepository, times(1)).findById("1");
        verify(accountsRepository, times(1)).save(account);

        assertThat(depositResponse.getCurrentBalance()).isEqualTo(2_000D);
    }

    @Test
    void makeDeposit_accountNotFound() {
        when(accountsRepository.findById("1")).thenReturn(Optional.empty());

        DepositResponse depositResponse = depositService.makeDeposit("1", new DepositRequest(1_000D));

        verify(accountsRepository, times(1)).findById("1");
        verify(accountsRepository, never()).save(any());

        assertThat(depositResponse.getAccountId()).isEqualTo("Account not found");
        assertThat(depositResponse.getCurrentBalance()).isEqualTo(0D);
    }
}