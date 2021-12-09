package com.sngular.omb.ombapi.controller;

import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.model.request.WithdrawRequest;
import com.sngular.omb.ombapi.model.response.ResponseWrapper;
import com.sngular.omb.ombapi.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAccounts() {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .data(accountsService.getAccounts()).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createAccount(@Valid @RequestBody Account account) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .data(accountsService.upsertAccount(account)).build(), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ResponseWrapper> getAccount(@PathVariable String accountId) {
        Optional<Account> account = accountsService.getAccount(accountId);

        if (account.isPresent()) {
            return new ResponseEntity<>(ResponseWrapper.builder()
                    .data(account.get()).build(), HttpStatus.OK);
        } else {
            log.error("Account with id '{}' not found", accountId);

            return new ResponseEntity<>(ResponseWrapper.builder()
                    .error("The given account identifier was not found").build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{accountId}/withdrawal")
    public ResponseEntity<ResponseWrapper> withdrawBalance(@PathVariable String accountId, @Valid @RequestBody WithdrawRequest account) {
        Optional<Account> accountOptional = accountsService.getAccount(accountId);

        if (accountOptional.isPresent()) {
            Account accountToUpdate = accountOptional.get();
            accountToUpdate.setCurrentBalance(accountToUpdate.getCurrentBalance() - account.getAmount());

            return new ResponseEntity<>(ResponseWrapper.builder()
                    .data(accountsService.upsertAccount(accountToUpdate)).build(), HttpStatus.OK);
        } else {
            log.error("Account with id '{}' not found", accountId);
            return new ResponseEntity<>(ResponseWrapper.builder()
                    .error("The given account identifier was not found").build(), HttpStatus.NOT_FOUND);
        }
    }
}
