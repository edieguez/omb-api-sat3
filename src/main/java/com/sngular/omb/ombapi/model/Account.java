package com.sngular.omb.ombapi.model;

import com.sngular.omb.ombapi.model.response.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String routingNumber;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String openDate;

    @NotNull
    private Double currentBalance;

    @NotNull
    private AccountType accountType;
}
