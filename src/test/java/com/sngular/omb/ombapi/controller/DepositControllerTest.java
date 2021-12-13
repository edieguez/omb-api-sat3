package com.sngular.omb.ombapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sngular.omb.ombapi.model.request.DepositRequest;
import com.sngular.omb.ombapi.model.response.DepositResponse;
import com.sngular.omb.ombapi.service.DepositService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepositController.class)
class DepositControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepositService depositService;

    @Test
    void postDeposit() throws Exception {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(100D);

        when(depositService.makeDeposit(anyString(), any(DepositRequest.class))).thenReturn(new DepositResponse("1", 1_000D));

        mockMvc.perform(post("/accounts/1/deposit")
                    .contentType("application/json")
                    .content(new ObjectMapper().writeValueAsString(depositRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.currentBalance").value(1_000D));
        ;
    }
}