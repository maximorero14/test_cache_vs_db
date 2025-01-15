package com.maximorero.test_cache_vs_db.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @Column(name = "active", nullable = false)
    private boolean active;

    public Account() {
    }

    public Account(String account, boolean active) {
        this.account = account;
        this.active = active;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}