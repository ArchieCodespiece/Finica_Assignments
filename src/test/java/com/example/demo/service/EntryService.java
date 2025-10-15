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
        Jornalentry task = new Jornalentry();
        task.setTaskDescription("Test Task");

        when(repository.save(task)).thenReturn(task);

        Jornalentry saved = service.save(task);
        assertEquals("Test Task", saved.getTaskDescription());
        verify(repository, times(1)).save(task);
    }

    @Test
    void testGetAll() {
        Jornalentry t1 = new Jornalentry();
        t1.setTaskDescription("Task 1");
        Jornalentry t2 = new Jornalentry();
        t2.setTaskDescription("Task 2");

        when(repository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Jornalentry> allTasks = service.getAll();
        assertEquals(2, allTasks.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Jornalentry t = new Jornalentry();
        t.setId(1L);
        t.setTaskDescription("Task 1");

        when(repository.findById(1L)).thenReturn(Optional.of(t));

        Jornalentry found = service.getById(1L);
        assertNotNull(found);
        assertEquals("Task 1", found.getTaskDescription());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        Jornalentry t = new Jornalentry();
        t.setId(1L);
        t.setTaskDescription("Updated Task");

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(t)).thenReturn(t);

        Jornalentry updated = service.update(t);
        assertNotNull(updated);
        assertEquals("Updated Task", updated.getTaskDescription());
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(t);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        service.delete(id);
        verify(repository, times(1)).deleteById(id);
    }
}
