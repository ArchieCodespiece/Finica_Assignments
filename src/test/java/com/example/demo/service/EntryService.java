package com.example.demo.service;

import com.example.demo.entity.Jornalentry;
import com.example.demo.repository.Entryrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EntryserviceTest {

    @Mock
    private Entryrepo repository;

    @InjectMocks
    private Entryservice service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Jornalentry user = new Jornalentry("user1", "pass1", Arrays.asList("Task A"));
        when(repository.save(user)).thenReturn(user);

        Jornalentry saved = service.save(user);
        assertNotNull(saved);
        assertEquals("user1", saved.getUsername());
        assertEquals(1, saved.getTasks().size());
        verify(repository, times(1)).save(user);
    }

    @Test
    void testGetAll() {
        Jornalentry u1 = new Jornalentry("user1", "pass1", Arrays.asList("Task 1", "Task 2"));
        Jornalentry u2 = new Jornalentry("user2", "pass2", Arrays.asList("Task 3"));

        when(repository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Jornalentry> allUsers = service.getAll();
        assertEquals(2, allUsers.size());
        assertEquals("user1", allUsers.get(0).getUsername());
        assertEquals(2, allUsers.get(0).getTasks().size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetByIdExists() {
        Jornalentry user = new Jornalentry("user1", "pass1", Arrays.asList("Task 1"));
        user.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        Jornalentry found = service.getById(1L);
        assertNotNull(found);
        assertEquals("user1", found.getUsername());
        assertEquals(1, found.getTasks().size());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotExists() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Jornalentry result = service.getById(99L);
        assertNull(result);
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void testUpdate() {
        Jornalentry user = new Jornalentry("user1", "newpass", Arrays.asList("Updated Task"));
        user.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(user)).thenReturn(user);

        Jornalentry updated = service.update(user);
        assertNotNull(updated);
        assertEquals("newpass", updated.getPassword());
        assertEquals(1, updated.getTasks().size());
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(user);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        service.delete(id);
        verify(repository, times(1)).deleteById(id);
    }
}
