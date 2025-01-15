package com.maximorero.test_cache_vs_db.repository;

import com.maximorero.test_cache_vs_db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccount(String account);
}