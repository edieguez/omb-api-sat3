package com.sngular.omb.ombapi.controller;

import com.sngular.omb.ombapi.model.Account;
import com.sngular.omb.ombapi.model.request.WithdrawRequest;
import com.sngular.omb.ombapi.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping
    public List<Account> getAccounts() {
        return accountsService.getAccounts();
    }

    @PostMapping
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountsService.upsertAccount(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getAccount(@PathVariable String accountId) {
        Optional<Account> account = accountsService.getAccount(accountId);

        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            log.error("Account with id '{}' not found", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The given account identifier was not found");
        }
    }

    @PutMapping("/{accountId}/withdrawal")
    public ResponseEntity<Object> withdrawBalance(@PathVariable String accountId, @Valid @RequestBody WithdrawRequest account) {
        Optional<Account> accountOptional = accountsService.getAccount(accountId);

        if (accountOptional.isPresent()) {
            Account accountToUpdate = accountOptional.get();
            accountToUpdate.setCurrentBalance(accountToUpdate.getCurrentBalance() - account.getAmount());

            return new ResponseEntity<>(accountsService.upsertAccount(accountToUpdate), HttpStatus.OK);
        } else {
            log.error("Account with id '{}' not found", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The given account identifier was not found");
        }
    }
}
