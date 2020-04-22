package com.github.viniciusfcf.ifood.cadastro;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class CadastroTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:12.2");

    @Override
    public Map<String, String> start() {
        POSTGRES.start();
        Map<String, String> propriedades = new HashMap<String, String>();

        //Banco de dados
        propriedades.put("quarkus.datasource.url", POSTGRES.getJdbcUrl());
        propriedades.put("quarkus.datasource.username", POSTGRES.getUsername());
        propriedades.put("quarkus.datasource.password", POSTGRES.getPassword());

        return propriedades;
    }

    @Override
    public void stop() {
        if (POSTGRES != null && POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }
}
