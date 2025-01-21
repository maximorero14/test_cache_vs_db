package com.maximorero.test_cache_vs_db.service;

import com.maximorero.test_cache_vs_db.entity.Transaction;
import com.maximorero.test_cache_vs_db.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public void deleteTransaction(UUID id) {
        repository.deleteById(id);
    }

    public List<Transaction> insertRandomTransactions(int count, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());

        // Define some realistic options for transaction data
        List<String> transactionTypes = Arrays.asList("Purchase", "Refund", "Withdrawal", "Deposit", "Transfer");
        List<String> statuses = Arrays.asList("Pending", "Completed", "Failed", "Cancelled", "Processing");
        List<String> countries = Arrays.asList("US", "UK", "Germany", "France", "Japan", "India", "Canada", "Brazil", "Australia", "Italy");

        Random random = new Random();

        IntStream.range(0, count).forEach(i -> executor.submit(() -> {
            Transaction transaction = new Transaction();

            // Random transaction type
            transaction.setTransactionType(transactionTypes.get(random.nextInt(transactionTypes.size())));

            // Random account number with some repetition
            transaction.setAccountNumber("AccNumber" + random.nextInt(count / 10));

            // Random status
            transaction.setStatus(statuses.get(random.nextInt(statuses.size())));

            // Random amount between 1 and 10,000
            transaction.setAmount(1 + (random.nextDouble() * 9999));

            // Random country
            transaction.setCountry(countries.get(random.nextInt(countries.size())));

            // Random date within the past 30 days
            LocalDateTime randomDate = LocalDateTime.now().minusDays(random.nextInt(30)).minusHours(random.nextInt(24)).minusMinutes(random.nextInt(60));
            transaction.setDate(randomDate);

            // Process date 1-5 days after the transaction date
            transaction.setProcessDate(randomDate.plusDays(1 + random.nextInt(5)));

            // Save transaction
            repository.save(transaction);

            // Add to the list
            transactions.add(transaction);
        }));

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while inserting transactions in parallel", e);
        }

        return transactions;
    }


}