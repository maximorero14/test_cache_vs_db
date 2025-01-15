package com.maximorero.test_cache_vs_db.service;

import com.maximorero.test_cache_vs_db.entity.Account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {

    public static List<Account> loadAccountsFromCSV(String filePath) {
        List<Account> accounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // para saltar la cabecera

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length == 2) {
                    String accountStr = fields[0].trim();
                    boolean active = Boolean.parseBoolean(fields[1].trim());
                    accounts.add(new Account(accountStr, active));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}