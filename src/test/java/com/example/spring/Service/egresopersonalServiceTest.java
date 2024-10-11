/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.Model.EgresoPersonal;
import com.example.spring.Repository.EgresoPersonalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EgresoPersonalServiceTest {

    @InjectMocks
    private egresopersonalService egresoPersonalService;

    @Mock
    private EgresoPersonalRepository egresoPersonalRepository;

    private EgresoPersonal egresoPersonal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        egresoPersonal = new EgresoPersonal();
        egresoPersonal.setId(1L);
        egresoPersonal.setDocumento(123456789L);
        egresoPersonal.setFechaHoraIngreso(LocalDateTime.now());
    }

    @Test
    void findAll_ReturnsListOfEgresoPersonal() {
        when(egresoPersonalRepository.findAll()).thenReturn(Arrays.asList(egresoPersonal));

        List<EgresoPersonal> result = egresoPersonalService.findAll();

        assertEquals(1, result.size());
        assertEquals(egresoPersonal, result.get(0));
    }

    @Test
    void findById_ReturnsEgresoPersonal() {
        when(egresoPersonalRepository.findById(1L)).thenReturn(Optional.of(egresoPersonal));

        Optional<EgresoPersonal> result = egresoPersonalService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(egresoPersonal, result.get());
    }

    @Test
    void findById_ReturnsEmpty() {
        when(egresoPersonalRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EgresoPersonal> result = egresoPersonalService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ReturnsSavedEgresoPersonal() {
        when(egresoPersonalRepository.save(egresoPersonal)).thenReturn(egresoPersonal);

        EgresoPersonal result = egresoPersonalService.save(egresoPersonal);

        assertEquals(egresoPersonal, result);
        verify(egresoPersonalRepository).save(egresoPersonal);
    }

    @Test
    void existsById_ReturnsTrue() {
        when(egresoPersonalRepository.existsById(1L)).thenReturn(true);

        boolean result = egresoPersonalService.existsById(1L);

        assertTrue(result);
    }

    @Test
    void existsById_ReturnsFalse() {
        when(egresoPersonalRepository.existsById(1L)).thenReturn(false);

        boolean result = egresoPersonalService.existsById(1L);

        assertFalse(result);
    }

    @Test
    void deleteById_CallsDelete() {
        doNothing().when(egresoPersonalRepository).deleteById(1L);

        egresoPersonalService.deleteById(1L);

        verify(egresoPersonalRepository).deleteById(1L);
    }
}
