package com.example.demo.controller;

import com.example.demo.entity.Jornalentry;
import com.example.demo.service.Entryservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntryControllerTest {

    @Mock
    private Entryservice entryService;

    @InjectMocks
    private EntryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        Jornalentry u1 = new Jornalentry("user1", "pass1", Arrays.asList("Task A", "Task B"));
        Jornalentry u2 = new Jornalentry("user2", "pass2", Arrays.asList("Task C"));

        when(entryService.getAll()).thenReturn(Arrays.asList(u1, u2));

        List<Jornalentry> result = controller.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals(2, result.get(0).getTasks().size());
        verify(entryService, times(1)).getAll();
    }

    @Test
    void testGetUserByIdFound() {
        Jornalentry user = new Jornalentry("user1", "pass1", Arrays.asList("Task A"));
        user.setId(1L);

        when(entryService.getById(1L)).thenReturn(user);

        ResponseEntity<Jornalentry> response = controller.getUserById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("user1", response.getBody().getUsername());
        assertEquals(1, response.getBody().getTasks().size());
        verify(entryService, times(1)).getById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(entryService.getById(99L)).thenReturn(null);

        ResponseEntity<Jornalentry> response = controller.getUserById(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(entryService, times(1)).getById(99L);
    }

    @Test
    void testCreateUser() {
        Jornalentry user = new Jornalentry("user3", "pass3", Arrays.asList("Task X"));

        when(entryService.save(user)).thenReturn(user);

        ResponseEntity<Jornalentry> response = controller.createUser(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("user3", response.getBody().getUsername());
        assertEquals(1, response.getBody().getTasks().size());
        verify(entryService, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        Jornalentry user = new Jornalentry("user1", "newpass", Arrays.asList("Updated Task"));
        user.setId(1L);

        when(entryService.update(user)).thenReturn(user);

        ResponseEntity<Jornalentry> response = controller.updateUser(1L, user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("newpass", response.getBody().getPassword());
        assertEquals(1, response.getBody().getTasks().size());
        verify(entryService, times(1)).update(user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(entryService).delete(1L);

        ResponseEntity<String> response = controller.deleteUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully", response.getBody());
        verify(entryService, times(1)).delete(1L);
    }
}
