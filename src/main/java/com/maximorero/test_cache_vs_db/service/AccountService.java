package com.maximorero.test_cache_vs_db.service;

import com.maximorero.test_cache_vs_db.entity.Account;
import com.maximorero.test_cache_vs_db.repository.AccountRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // Caché local
    private final Map<String, Boolean> localCache = new HashMap<>();

    public AccountService(AccountRepository accountRepository,
                          RedisTemplate<String, String> redisTemplate) {
        this.accountRepository = accountRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Crea un archivo CSV con 5000 registros, formato: account,active
     */
    public void createCsvFile(String filePath) {
        int NUM_CUENTAS = 5000;
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
        int ACCOUNT_LENGTH = 8;
        Random random = new Random();

        try (FileWriter writer = new FileWriter(filePath)) {
            // Cabecera del CSV
            writer.write("account,active\n");

            for (int i = 0; i < NUM_CUENTAS; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < ACCOUNT_LENGTH; j++) {
                    sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
                }
                boolean active = random.nextBoolean();
                writer.write(sb.toString() + "," + active + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee el CSV y carga las cuentas en la caché local.
     */
    public void loadToLocalCache(String filePath) {
        List<Account> accounts = CSVLoader.loadAccountsFromCSV(filePath);
        accounts.forEach(a -> localCache.put(a.getAccount(), a.isActive()));
        System.out.println("Datos cargados en caché local (Map).");
    }

    /**
     * Lee el CSV y carga las cuentas en la base de datos PostgreSQL.
     */
    public void loadToDatabase(String filePath) {
        List<Account> accounts = CSVLoader.loadAccountsFromCSV(filePath);

        // Guardar en BD solo las cuentas que no existan
        for (Account acc : accounts) {
            if (accountRepository.findByAccount(acc.getAccount()).isEmpty()) {
                accountRepository.save(acc);
            }
        }
        System.out.println("Datos cargados en la base de datos PostgreSQL.");
    }

    /**
     * Lee el CSV y carga las cuentas en Redis.
     */
    public void loadToRedis(String filePath) {
        List<Account> accounts = CSVLoader.loadAccountsFromCSV(filePath);
        accounts.forEach(a -> {
            String activeAsString = Boolean.toString(a.isActive()); // Convertimos Boolean a String
            redisTemplate.opsForValue().set(a.getAccount(), activeAsString); // Almacenamos como String
        });
        System.out.println("Datos cargados en Redis.");
    }

    // ----------------------------------------------------------------
    // Métodos para consultar cada fuente de datos
    // ----------------------------------------------------------------

    public Boolean getAccountFromLocalCache(String account) {
        return localCache.get(account);
    }

    public Boolean getAccountFromDatabase(String account) {
        return accountRepository.findByAccount(account)
                .map(Account::isActive)
                .orElse(null);
    }

    public Boolean getAccountFromRedis(String account) {
        return Boolean.valueOf(redisTemplate.opsForValue().get(account));
    }

    public List<Account> getAllAccountsFromCsv(String filePath) {
        return CSVLoader.loadAccountsFromCSV(filePath);
    }
}