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
    void testGetAllTasks() {
        Jornalentry t1 = new Jornalentry();
        t1.setTaskDescription("Task 1");
        Jornalentry t2 = new Jornalentry();
        t2.setTaskDescription("Task 2");

        when(entryService.getAll()).thenReturn(Arrays.asList(t1, t2));

        List<Jornalentry> result = controller.getAllTasks();
        assertEquals(2, result.size());
        verify(entryService, times(1)).getAll();
    }

    @Test
    void testGetTaskByIdFound() {
        Jornalentry task = new Jornalentry();
        task.setId(1L);
        task.setTaskDescription("Existing Task");

        when(entryService.getById(1L)).thenReturn(task);

        ResponseEntity<Jornalentry> response = controller.getTaskById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Existing Task", response.getBody().getTaskDescription());
        verify(entryService, times(1)).getById(1L);
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(entryService.getById(99L)).thenReturn(null);

        ResponseEntity<Jornalentry> response = controller.getTaskById(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(entryService, times(1)).getById(99L);
    }

    @Test
    void testCreateTask() {
        Jornalentry task = new Jornalentry();
        task.setTaskDescription("New Task");

        when(entryService.save(task)).thenReturn(task);

        ResponseEntity<Jornalentry> response = controller.createTask(task);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("New Task", response.getBody().getTaskDescription());
        verify(entryService, times(1)).save(task);
    }

    @Test
    void testUpdateTask() {
        Jornalentry task = new Jornalentry();
        task.setTaskDescription("Updated Task");

        when(entryService.update(any())).thenReturn(task);

        ResponseEntity<Jornalentry> response = controller.updateTask(1L, task);
        assertEquals(200, response.getStatusCodeValue());
        verify(entryService, times(1)).update(any());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(entryService).delete(1L);

        ResponseEntity<String> response = controller.deleteTask(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted successfully", response.getBody());
        verify(entryService, times(1)).delete(1L);
    }
}
