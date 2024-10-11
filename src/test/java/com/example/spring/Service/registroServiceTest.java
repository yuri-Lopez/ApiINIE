/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.Model.Registro;
import com.example.spring.Repository.registroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class registroServiceTest {

    @InjectMocks
    private registroService registroService;

    @Mock
    private registroRepository registroRepository;

    private Registro registro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registro = new Registro();
        registro.setId(1L);
        // Asignar otros valores a `registro` si es necesario
    }

    @Test
    void getAllRegistros_ReturnsListOfRegistros() {
        when(registroRepository.findAll()).thenReturn(Collections.singletonList(registro));

        List<Registro> registros = registroService.getAllRegistros();

        assertNotNull(registros);
        assertEquals(1, registros.size());
        assertEquals(registro, registros.get(0));
    }

    @Test
    void getRegistroById_ExistingId_ReturnsRegistro() {
        when(registroRepository.findById(anyLong())).thenReturn(Optional.of(registro));

        Optional<Registro> result = registroService.getRegistroById(1L);

        assertTrue(result.isPresent());
        assertEquals(registro, result.get());
    }

    @Test
    void getRegistroById_NonExistingId_ReturnsEmpty() {
        when(registroRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Registro> result = registroService.getRegistroById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void createRegistro_SavesRegistro() {
        when(registroRepository.save(any(Registro.class))).thenReturn(registro);

        Registro result = registroService.createRegistro(registro);

        assertEquals(registro, result);
        verify(registroRepository).save(registro);
    }

    @Test
    void updateRegistro_ExistingId_UpdatesAndReturnsRegistro() {
        when(registroRepository.existsById(anyLong())).thenReturn(true);
        when(registroRepository.save(any(Registro.class))).thenReturn(registro);

        Registro result = registroService.updateRegistro(1L, registro);

        assertEquals(registro, result);
        verify(registroRepository).save(registro);
    }

    @Test
    void updateRegistro_NonExistingId_ThrowsException() {
        when(registroRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            registroService.updateRegistro(2L, registro);
        });

        assertEquals("Registro not found with id: 2", exception.getMessage());
    }

    @Test
    void deleteRegistro_ExistingId_DeletesRegistro() {
        when(registroRepository.existsById(anyLong())).thenReturn(true);

        registroService.deleteRegistro(1L);

        verify(registroRepository).deleteById(1L);
    }

    @Test
    void deleteRegistro_NonExistingId_ThrowsException() {
        when(registroRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            registroService.deleteRegistro(2L);
        });

        assertEquals("Registro not found with id: 2", exception.getMessage());
    }
}
