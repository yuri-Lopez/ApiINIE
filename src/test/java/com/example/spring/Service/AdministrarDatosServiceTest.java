/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.spring.Service;

import com.example.spring.Model.AdministrarDatos;
import com.example.spring.Repository.AdministrarDatosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdministrarDatosServiceTest {

    @InjectMocks
    private AdministrarDatosService administrarDatosService;

    @Mock
    private AdministrarDatosRepository administrarDatosRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void traerdato_deberiaRetornarListaDeDatos() {
        // Arrange
        List<AdministrarDatos> datos = new ArrayList<>();
        AdministrarDatos dato = new AdministrarDatos();
        dato.setNombre("Maria");
        dato.setApellido("Fernandez");
        dato.setDocumento("675432");
        dato.setCorreoElectronico("mariaf@hotmail.com");
        dato.setTelefono("31245678");
        dato.setPlanillaSeguridadSocial("23");
        datos.add(dato);
        when(administrarDatosRepository.findAll()).thenReturn(datos);

        // Act
        List<AdministrarDatos> resultado = administrarDatosService.traerdato();

        // Assert
        assertEquals(1, resultado.size());
        verify(administrarDatosRepository).findAll();
    }

    @Test
    void guardardato_datoValido_deberiaGuardarExitosamente() {
        // Arrange
        AdministrarDatos dato = new AdministrarDatos();
        dato.setNombre("Maria");
        dato.setApellido("Fernandez");
        dato.setDocumento("675432");
        dato.setCorreoElectronico("mariaf@hotmail.com");
        dato.setTelefono("31245678");
        dato.setPlanillaSeguridadSocial("23");
        when(administrarDatosRepository.save(any(AdministrarDatos.class))).thenReturn(dato);

        // Act
        String resultado = administrarDatosService.guardardato(dato);

        // Assert
        assertEquals("Guardado exitosamente", resultado);
        verify(administrarDatosRepository).save(dato);
    }

    @Test
    void guardardato_datoInvalido_deberiaRetornarError() {
        // Arrange
        AdministrarDatos dato = new AdministrarDatos(); // Campos no inicializados

        // Act
        String resultado = administrarDatosService.guardardato(dato);

        // Assert
        assertEquals("Error: Todos los campos obligatorios deben estar presentes", resultado);
        verify(administrarDatosRepository, never()).save(any());
    }

    @Test
    void actualizardato_datoExistente_deberiaActualizarCorrectamente() {
        // Arrange
        AdministrarDatos datoExistente = new AdministrarDatos();
        datoExistente.setNombre("Maria");
        datoExistente.setApellido("Fernandez");
        datoExistente.setDocumento("675432");
        when(administrarDatosRepository.findByDocumento("675432")).thenReturn(datoExistente);

        AdministrarDatos datoActualizar = new AdministrarDatos();
        datoActualizar.setNombre("Juan");
        datoActualizar.setApellido("Pérez");
        datoActualizar.setDocumento("675432");

        // Act
        String resultado = administrarDatosService.actualizardato(datoActualizar);

        // Assert
        assertEquals("Se actualizó correctamente", resultado);
        verify(administrarDatosRepository).save(datoExistente);
    }

    @Test
    void eliminardato_datoExistente_deberiaEliminarCorrectamente() {
        // Arrange
        AdministrarDatos datoExistente = new AdministrarDatos();
        datoExistente.setNombre("Maria");
        datoExistente.setApellido("Fernandez");
        datoExistente.setDocumento("675432");
        when(administrarDatosRepository.findByDocumento("675432")).thenReturn(datoExistente);

        // Act
        String resultado = administrarDatosService.eliminardato("675432");

        // Assert
        assertEquals("Dato eliminado correctamente", resultado);
        verify(administrarDatosRepository).delete(datoExistente);
    }

    @Test
    void eliminardato_datoNoExistente_deberiaRetornarError() {
        // Arrange
        when(administrarDatosRepository.findByDocumento("675432")).thenReturn(null);

        // Act
        String resultado = administrarDatosService.eliminardato("675432");

        // Assert
        assertEquals("El dato no existe", resultado);
        verify(administrarDatosRepository, never()).delete(any());
    }
}
