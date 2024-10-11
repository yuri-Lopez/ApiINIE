/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.Model.IngresoPersonal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngresoPersonalServiceTest {

    @InjectMocks
    private IngresoPersonalService ingresoPersonalService;

    @Mock
    private JpaRepository<IngresoPersonal, Long> repository;

    private IngresoPersonal ingresoPersonal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingresoPersonal = new IngresoPersonal();
        ingresoPersonal.setId(1L);
        ingresoPersonal.setDocumento(123456);
        ingresoPersonal.setFechaHoraIngreso(LocalDateTime.now());
    }

    @Test
    void findAll_ReturnsListOfIngresoPersonal() {
        when(repository.findAll()).thenReturn(Collections.singletonList(ingresoPersonal));

        List<IngresoPersonal> result = ingresoPersonalService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ingresoPersonal, result.get(0));
    }

    @Test
    void findById_ReturnsIngresoPersonal_WhenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(ingresoPersonal));

        Optional<IngresoPersonal> result = ingresoPersonalService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(ingresoPersonal, result.get());
    }

    @Test
    void findById_ReturnsEmpty_WhenNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<IngresoPersonal> result = ingresoPersonalService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ReturnsSavedIngresoPersonal() {
        when(repository.save(ingresoPersonal)).thenReturn(ingresoPersonal);

        IngresoPersonal result = ingresoPersonalService.save(ingresoPersonal);

        assertEquals(ingresoPersonal, result);
        verify(repository).save(ingresoPersonal);
    }

    @Test
    void deleteById_CallsDeleteOnRepository() {
        doNothing().when(repository).deleteById(1L);

        ingresoPersonalService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
