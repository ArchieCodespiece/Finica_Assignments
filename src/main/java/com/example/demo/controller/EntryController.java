package com.example.demo.controller;

import com.example.demo.entity.Jornalentry;
import com.example.demo.service.Entryservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class EntryController {  // Renamed for clarity

    private final Entryservice entryService;

    public EntryController(Entryservice entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public List<Jornalentry> getAllTasks() {
        return entryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jornalentry> getTaskById(@PathVariable Long id) {
        Jornalentry task = entryService.getById(id); // implement getById in service
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Jornalentry> createTask(@RequestBody Jornalentry task) {
        Jornalentry created = entryService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jornalentry> updateTask(@PathVariable Long id, @RequestBody Jornalentry taskDetails) {
        taskDetails.setId(id);
        Jornalentry updated = entryService.update(taskDetails); // must handle null inside service
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        entryService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
