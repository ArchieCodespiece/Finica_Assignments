package com.example.demo.service;

import com.example.demo.entity.Jornalentry;
import com.example.demo.repository.Entryrepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Entryservice { // Constructor name must match class

    private final Entryrepo repository;

    public Entryservice(Entryrepo repository) {
        this.repository = repository;
    }

    public Jornalentry save(Jornalentry j) {
        return repository.save(j);
    }

    public List<Jornalentry> getAll() {
        return repository.findAll();
    }

    public Jornalentry update(Jornalentry j) {
        return repository.save(j);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
