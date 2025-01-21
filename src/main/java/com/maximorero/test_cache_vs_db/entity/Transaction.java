package com.maximorero.test_cache_vs_db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private UUID internalId;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "process_date", nullable = false)
    private LocalDateTime processDate;

    // Getters and Setters

    public UUID getInternalId() {
        return internalId;
    }

    public void setInternalId(UUID internalId) {
        this.internalId = internalId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDateTime processDate) {
        this.processDate = processDate;
    }

    // Constructors

    public Transaction() {
    }

    public Transaction(UUID internalId, String transactionType, String accountNumber, String status, Double amount, String country, LocalDateTime date, LocalDateTime processDate) {
        this.internalId = internalId;
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.status = status;
        this.amount = amount;
        this.country = country;
        this.date = date;
        this.processDate = processDate;
    }
}
