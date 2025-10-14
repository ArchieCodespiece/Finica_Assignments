package com.example.demo.controller;

import com.example.demo.entity.Jornalentry;
import com.example.demo.service.Entryservice;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class Entry {

  private final Entryservice entryService;

  // Constructor injection for service
  public Entry(Entryservice entryService) {
    this.entryService = entryService;
  }

  // Create a new task
  @PostMapping
  public ResponseEntity<Jornalentry> create(@RequestBody Jornalentry myentry) {
    Jornalentry saved = entryService.save(myentry);
    return ResponseEntity.ok(saved);
  }

  // Get all tasks
  @GetMapping
  public List<Jornalentry> getAll() {
    return entryService.getAll();
  }

  // Update a task by id
  @PutMapping("/{id}")
  public ResponseEntity<Jornalentry> update(@PathVariable long id, @RequestBody Jornalentry myentry) {
    myentry.setId(id);
    Jornalentry updated = entryService.update(myentry);
    return ResponseEntity.ok(updated);
  }

  // Delete a task by id
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable long id) {
    entryService.delete(id);
    return ResponseEntity.ok("Deleted successfully");
  }
}
