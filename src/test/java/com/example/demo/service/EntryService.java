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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EntryserviceTest {

    @Mock
    private Entryrepo repository;

    @InjectMocks
    private Entryservice service; // must be Entryservice, not Entryrepo

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
    void testDelete() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        service.delete(id);
        verify(repository, times(1)).deleteById(id);
    }
}
