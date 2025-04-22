package com.example.bankapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;


@Testcontainers
@SpringBootTest
public class BankappApplicationTests {

    // Use Testcontainers for local development
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        if (isRunningInCI()) {
            // Use RDS in CI environment
            registry.add("spring.datasource.url", 
                () -> "jdbc:postgresql://corporateproject-postgres.cd0yoy806w7s.ap-south-1.rds.amazonaws.com:5432/corporateproject_db");
            registry.add("spring.datasource.username", () -> "pgadmin");
            registry.add("spring.datasource.password", () -> System.getenv("DB_PASSWORD")); // Get from environment
        } else {
            // Use Testcontainers for local development
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
        }
    }

    private static boolean isRunningInCI() {
        return System.getenv("CI") != null && System.getenv("CI").equals("true");
    }

    @Test
    void contextLoads() {
        // Test will automatically use the appropriate database
    }
}
