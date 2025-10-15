package com.example.demo.service;

import com.example.demo.entity.Jornalentry;
import com.example.demo.repository.Entryrepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Entryservice {

    private final Entryrepo entryrepo;

    public Entryservice(Entryrepo entryrepo) {
        this.entryrepo = entryrepo;
    }

    public List<Jornalentry> getAll() {
        return entryrepo.findAll();
    }

    public Jornalentry getById(Long id) {
        Optional<Jornalentry> entry = entryrepo.findById(id);
        return entry.orElse(null);
    }

    public Jornalentry save(Jornalentry entry) {
        return entryrepo.save(entry);
    }

    public Jornalentry update(Jornalentry entryDetails) {
        if (entryrepo.existsById(entryDetails.getId())) {
            return entryrepo.save(entryDetails);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        entryrepo.deleteById(id);
    }
}
