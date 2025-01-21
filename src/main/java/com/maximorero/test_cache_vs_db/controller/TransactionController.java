package com.maximorero.test_cache_vs_db.controller;

import com.maximorero.test_cache_vs_db.entity.Account;
import com.maximorero.test_cache_vs_db.entity.Transaction;
import com.maximorero.test_cache_vs_db.service.AccountService;
import com.maximorero.test_cache_vs_db.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) {
        Transaction transaction = service.getTransactionById(id);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return service.saveTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        service.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate-random")
    public ResponseEntity<List<Transaction>> generateRandomTransactions(@RequestParam int count, @RequestParam int threads) {
        List<Transaction> transactions = service.insertRandomTransactions(count, threads);
        return ResponseEntity.ok(transactions);
    }
}
