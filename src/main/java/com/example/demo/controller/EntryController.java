package com.example.demo.controller;

import com.example.demo.Security.JwtUtil;
import com.example.demo.entity.Jornalentry;
import com.example.demo.service.Entryservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class EntryController {

    private final Entryservice entryService;

    public EntryController(Entryservice entryService) {
        this.entryService = entryService;
    }

    // ---------------- USER CRUD ----------------

    @GetMapping
    public List<Jornalentry> getAllUsers() {
        return entryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jornalentry> getUserById(@PathVariable Long id) {
        Jornalentry user = entryService.getById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Jornalentry> createUser(@RequestBody Jornalentry user) {
        Jornalentry created = entryService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jornalentry> updateUser(@PathVariable Long id, @RequestBody Jornalentry userDetails) {
        userDetails.setId(id);
        Jornalentry updated = entryService.update(userDetails);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        entryService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // ---------------- LOGIN / LOGOUT ----------------

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Jornalentry loginRequest) {
        Jornalentry user = entryService.getAll().stream()
                .filter(u -> u.getUsername().equals(loginRequest.getUsername())
                        && u.getPassword().equals(loginRequest.getPassword()))
                .findFirst()
                .orElse(null);

        if (user != null) {
            String token = JwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token); // return JWT
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token missing");
        }
        return ResponseEntity.ok("Logout successful. Please discard the token on client side.");
    }

    // ---------------- TASKS ----------------

    @GetMapping("/tasks")
    public ResponseEntity<List<String>> getTasks(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        Jornalentry user = entryService.getAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (user == null || !JwtUtil.validateToken(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(user.getTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> addTask(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody String taskDescription) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        Jornalentry user = entryService.getAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (user == null || !JwtUtil.validateToken(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<String> tasks = user.getTasks();
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(taskDescription);
        user.setTasks(tasks);

        entryService.update(user); // save updated tasks

        return ResponseEntity.ok("Task added successfully");
    }
}
