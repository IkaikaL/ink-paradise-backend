package com.catsaredope.inkparadise.Controllers;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catsaredope.inkparadise.Models.Account;
import com.catsaredope.inkparadise.Repositories.AccountRepository;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId) throws Exception {
        if (accountId == null) {
            throw new IllegalArgumentException("Account id cannot be null");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {
        if (account.getEmail() == null || account.getPassword() == null || account.getUserName() == null) {
            throw new IllegalArgumentException("Account email, password, and username cannot be null");
        }
        return accountRepository.save(account);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
            @Valid @RequestBody Account accountDetails) throws Exception {
        if (accountId == null) {
            throw new IllegalArgumentException("Account id cannot be null");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));

        account.setEmail(accountDetails.getEmail());
        account.setPassword(accountDetails.getPassword());
        account.setUserName(accountDetails.getUserName());
        account.setContentFilter(accountDetails.getContentFilter());
        final Account updatedAccount = accountRepository.save(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/accounts/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId) throws Exception {
        if (accountId == null) {
            throw new IllegalArgumentException("Account id cannot be null");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        accountRepository.delete(account);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}