package com.example.demo.repository;

import com.example.demo.entity.Jornalentry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Entryrepo extends JpaRepository<Jornalentry, Long> {
}
