package com.maximorero.test_cache_vs_db.controller;

import com.maximorero.test_cache_vs_db.entity.Account;
import com.maximorero.test_cache_vs_db.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Busca en caché local
    @GetMapping("/local")
    public ResponseDTO getAccountFromLocal(@RequestParam String account) {
        Boolean isActive = accountService.getAccountFromLocalCache(account);
        return new ResponseDTO(account, isActive);
    }

    // Busca en BD
    @GetMapping("/db")
    public ResponseDTO getAccountFromDb(@RequestParam String account) {
        Boolean isActive = accountService.getAccountFromDatabase(account);
        return new ResponseDTO(account, isActive);
    }

    // Busca en Redis
    @GetMapping("/redis")
    public ResponseDTO getAccountFromRedis(@RequestParam String account) {
        Boolean isActive = accountService.getAccountFromRedis(account);
        return new ResponseDTO(account, isActive);
    }

    @GetMapping("/all-accounts-csv")
    public ResponseEntity<List<Account>> getAllAccounts() {
        String filePath = "cuentas.csv";
        List<Account> accounts = accountService.getAllAccountsFromCsv(filePath);
        return ResponseEntity.ok(accounts);
    }

    // DTO de respuesta
    static class ResponseDTO {
        private String account;
        private Boolean active;

        public ResponseDTO(String account, Boolean active) {
            this.account = account;
            this.active = active;
        }

        public String getAccount() {
            return account;
        }

        public Boolean getActive() {
            return active;
        }
    }
}