package com.example.demo.controller;

import com.example.demo.entity.Jornalentry;
import com.example.demo.database.connect;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class Entry {

  private connect db = new connect();

  @PostMapping("/api/tasks")
  public void create(@RequestBody Jornalentry myentry) {
    db.save(myentry);
  }

  @GetMapping("/api/tasks")
  public List<Jornalentry> getAll() {
    return db.getAll();
  }

  @PutMapping("/api/tasks/{id}")
  public void update(@PathVariable long id, @RequestBody Jornalentry myentry) {
    myentry.setId(id);
    db.update(myentry);
  }

  @DeleteMapping("/api/tasks/{id}")
  public void delete(@PathVariable long id) {
    db.delete(id);
  }
}
