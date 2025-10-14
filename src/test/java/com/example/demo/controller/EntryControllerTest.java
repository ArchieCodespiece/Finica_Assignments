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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EntryControllerTest {

    @Mock
    private Entryservice entryService;

    @InjectMocks
    private Entry controller; // matches your controller class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEntries() {
        Jornalentry j1 = new Jornalentry("Task 1");
        j1.setId(1L);

        Jornalentry j2 = new Jornalentry("Task 2");
        j2.setId(2L);

        List<Jornalentry> mockList = Arrays.asList(j1, j2);
        when(entryService.getAll()).thenReturn(mockList);

        List<Jornalentry> result = controller.getAll();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskDescription());
        verify(entryService, times(1)).getAll();
    }

    @Test
    void testSaveEntry() {
        Jornalentry input = new Jornalentry("New Task");

        Jornalentry saved = new Jornalentry("New Task");
        saved.setId(1L);

        when(entryService.save(any(Jornalentry.class))).thenReturn(saved);

        ResponseEntity<Jornalentry> response = controller.create(input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("New Task", response.getBody().getTaskDescription());
        verify(entryService, times(1)).save(input);
    }

    @Test
    void testUpdateEntry() {
        Jornalentry input = new Jornalentry("Updated Task");
        input.setId(1L);

        when(entryService.update(any(Jornalentry.class))).thenReturn(input);

        ResponseEntity<Jornalentry> response = controller.update(1L, input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Task", response.getBody().getTaskDescription());
        verify(entryService, times(1)).update(input);
    }

    @Test
    void testDeleteEntry() {
        long id = 1L;

        ResponseEntity<String> response = controller.delete(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted successfully", response.getBody());
        verify(entryService, times(1)).delete(id);
    }
}
