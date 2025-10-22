package com.example.demo.config;

import com.example.demo.entity.Jornalentry;
import com.example.demo.repository.Entryrepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(Entryrepo entryrepo, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (entryrepo.count() == 0) {
                Jornalentry user1 = new Jornalentry(
                        "alice",
                        passwordEncoder.encode("password123"),
                        Arrays.asList("Buy groceries", "Finish project")
                );
                Jornalentry user2 = new Jornalentry(
                        "bob",
                        passwordEncoder.encode("bobpass"),
                        Arrays.asList("Read book", "Go jogging")
                );
                Jornalentry admin = new Jornalentry(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        List.of("Review reports", "Approve tasks")
                );

                entryrepo.saveAll(List.of(user1, user2, admin));

                System.out.println("✅ Dummy users added: alice, bob, admin");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
