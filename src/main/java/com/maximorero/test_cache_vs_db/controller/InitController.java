package com.maximorero.test_cache_vs_db.controller;

import com.maximorero.test_cache_vs_db.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/init")
public class InitController {

    private final AccountService accountService;

    public InitController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 1. Crear el archivo CSV (5000 cuentas).
     *    POST /init/create-file
     */
    @PostMapping("/create-file")
    public ResponseEntity<String> createCsv() {
        String filePath = "cuentas.csv";
        accountService.createCsvFile(filePath);
        return ResponseEntity.ok("Archivo CSV creado en " + filePath);
    }

    /**
     * 2. Leer el CSV y cargarlo en caché local.
     *    POST /init/load-cache
     */
    @PostMapping("/load-cache")
    public ResponseEntity<String> loadCacheLocal() {
        String filePath = "cuentas.csv";
        accountService.loadToLocalCache(filePath);
        return ResponseEntity.ok("Cuentas cargadas a la caché local.");
    }

    /**
     * 3. Leer el CSV y cargarlo en la base de datos PostgreSQL.
     *    POST /init/load-database
     */
    @PostMapping("/load-database")
    public ResponseEntity<String> loadDatabase() {
        String filePath = "cuentas.csv";
        accountService.loadToDatabase(filePath);
        return ResponseEntity.ok("Cuentas cargadas a la base de datos.");
    }

    /**
     * 4. Leer el CSV y cargarlo en Redis.
     *    POST /init/load-redis
     */
    @PostMapping("/load-redis")
    public ResponseEntity<String> loadRedis() {
        String filePath = "cuentas.csv";
        accountService.loadToRedis(filePath);
        return ResponseEntity.ok("Cuentas cargadas a Redis.");
    }

    /**
     * 5. Realiza todos los pasos anteriores de forma secuencial:
     *    - Crear CSV
     *    - Cargar a cache local
     *    - Cargar a base de datos
     *    - Cargar a Redis
     *
     *    POST /init/init-all
     */
    @PostMapping("/init-all")
    public ResponseEntity<String> initAll() {
        String filePath = "cuentas.csv";

        // 1) Crear CSV
        accountService.createCsvFile(filePath);

        // 2) Cargar en caché local
        accountService.loadToLocalCache(filePath);

        // 3) Cargar en base de datos
        accountService.loadToDatabase(filePath);

        // 4) Cargar en Redis
        accountService.loadToRedis(filePath);

        return ResponseEntity.ok("Proceso de init completado: CSV creado y cargado en (cache, DB, Redis).");
    }
}
