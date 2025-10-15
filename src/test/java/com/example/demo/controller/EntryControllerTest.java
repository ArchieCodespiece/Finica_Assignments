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
    private EntryController controller;

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

        when(entryService.getAll()).thenReturn(Arrays.asList(j1, j2));

        List<Jornalentry> result = controller.getAllTasks();
        assertEquals(2, result.size());
        verify(entryService, times(1)).getAll();
    }

    @Test
    void testSaveEntry() {
        Jornalentry input = new Jornalentry("New Task");
        Jornalentry saved = new Jornalentry("New Task");
        saved.setId(1L);

        when(entryService.save(any(Jornalentry.class))).thenReturn(saved);

        ResponseEntity<Jornalentry> response = controller.createTask(input);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("New Task", response.getBody().getTaskDescription());
        verify(entryService, times(1)).save(input);
    }

    @Test
    void testUpdateEntry() {
        Jornalentry input = new Jornalentry("Updated Task");
        input.setId(1L);

        when(entryService.update(any(Jornalentry.class))).thenReturn(input);

        ResponseEntity<Jornalentry> response = controller.updateTask(1L, input);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Task", response.getBody().getTaskDescription());
        verify(entryService, times(1)).update(input);
    }

    @Test
    void testDeleteEntry() {
        doNothing().when(entryService).delete(1L);

        ResponseEntity<String> response = controller.deleteTask(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted successfully", response.getBody());
        verify(entryService, times(1)).delete(1L);
    }
}
