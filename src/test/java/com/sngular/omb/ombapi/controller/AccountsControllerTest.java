package com.sngular.omb.ombapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.model.response.AccountType;
import com.sngular.omb.ombapi.service.AccountsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountsController.class)
class AccountsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsService accountsService;

    @Test
    void getAccounts() throws Exception {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        when(accountsService.getAccounts()).thenReturn(List.of(account));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].id").value("1"))
                .andExpect(jsonPath("$.data.[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data.[0].routingNumber").value("042"))
                .andExpect(jsonPath("$.data.[0].accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.[0].openDate").value("01-01-1970"))
                .andExpect(jsonPath("$.data.[0].currentBalance").value(1_000D))
                .andExpect(jsonPath("$.data.[0].accountType").value(AccountType.SAVINGS.name()));
        ;
    }

    @Test
    void createAccount() throws Exception {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        String requestBody = new ObjectMapper().writeValueAsString(account);

        when(accountsService.upsertAccount(account)).thenReturn(account);

        mockMvc.perform(post("/accounts")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.routingNumber").value("042"))
                .andExpect(jsonPath("$.data.accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.openDate").value("01-01-1970"))
                .andExpect(jsonPath("$.data.currentBalance").value(1_000D))
                .andExpect(jsonPath("$.data.accountType").value(AccountType.SAVINGS.name()));
    }

    @Test
    void getAccount() throws Exception {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        when(accountsService.getAccount("1")).thenReturn(Optional.of(account));

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.routingNumber").value("042"))
                .andExpect(jsonPath("$.data.accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.openDate").value("01-01-1970"))
                .andExpect(jsonPath("$.data.currentBalance").value(1_000D))
                .andExpect(jsonPath("$.data.accountType").value(AccountType.SAVINGS.name()));
    }

    @Test
    void getAccount_notFound() throws Exception {
        when(accountsService.getAccount("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void withdrawBalance() throws Exception {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        Account updatedAccount = new Account("1", "John Doe", "042", "12345", "01-01-1970", 900D, AccountType.SAVINGS);
        Map<String, String> request = new HashMap<>();
        request.put("amount", "100");

        String requestBody = new ObjectMapper().writeValueAsString(request);

        when(accountsService.getAccount("1")).thenReturn(Optional.of(account));
        when(accountsService.upsertAccount(any(Account.class))).thenReturn(updatedAccount);

        mockMvc.perform(put("/accounts/1/withdrawal")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.routingNumber").value("042"))
                .andExpect(jsonPath("$.data.accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.openDate").value("01-01-1970"))
                .andExpect(jsonPath("$.data.currentBalance").value(900D))
                .andExpect(jsonPath("$.data.accountType").value(AccountType.SAVINGS.name()));
    }

    @Test
    void withdrawBalance_notFound() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("amount", "100");

        String requestBody = new ObjectMapper().writeValueAsString(request);

        when(accountsService.getAccount("1")).thenReturn(Optional.empty());

        mockMvc.perform(put("/accounts/1/withdrawal")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void withdrawBalance_insufficientBalance() throws Exception {
        Account account = new Account("1", "John Doe", "042", "12345", "01-01-1970", 1_000D, AccountType.SAVINGS);
        Map<String, String> request = new HashMap<>();
        request.put("amount", "1001");

        String requestBody = new ObjectMapper().writeValueAsString(request);

        when(accountsService.getAccount("1")).thenReturn(Optional.of(account));

        mockMvc.perform(put("/accounts/1/withdrawal")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}