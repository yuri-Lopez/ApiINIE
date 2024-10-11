/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.AdministrarDatos;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.AdministrarDatosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AdministrarDatosControllerTest {

    @InjectMocks
    private AdministrarDatosController administrarDatosController;

    @Mock
    private AdministrarDatosService administrarDatosService;

    @Mock
    private JwtBalancer authbalan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void traerdato_autenticado_deberiaRetornarLista() {
        // Arrange
        String token = "validToken";
        when(authbalan.authjwt(token)).thenReturn(true);
        when(administrarDatosService.traerdato()).thenReturn(Collections.singletonList(new AdministrarDatos()));

        // Act
        ResponseEntity<?> response = administrarDatosController.traerdato(token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(administrarDatosService).traerdato();
    }

    @Test
    void traerdato_noAutenticado_deberiaRetornarUnauthorized() {
        // Arrange
        String token = "invalidToken";
        when(authbalan.authjwt(token)).thenReturn(false);

        // Act
        ResponseEntity<?> response = administrarDatosController.traerdato(token);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sin firmar"));
    }

    @Test
    void creardato_datoValido_deberiaRetornarCreado() {
        // Arrange
        String token = "validToken";
        AdministrarDatos dato = new AdministrarDatos();
        dato.setNombre("Maria");
        dato.setApellido("Fernandez");
        dato.setDocumento("675432");
        dato.setCorreoElectronico("mariaf@hotmail.com");
        dato.setTelefono("31245678");
        dato.setPlanillaSeguridadSocial("23");

        when(authbalan.authjwt(token)).thenReturn(true);
        when(administrarDatosService.guardardato(dato)).thenReturn("Guardado exitosamente");

        // Act
        ResponseEntity<?> response = administrarDatosController.creardato(token, dato);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Guardado exitosamente", response.getBody());
        verify(administrarDatosService).guardardato(dato);
    }

    @Test
    void creardato_datoInvalido_deberiaRetornarBadRequest() {
        // Arrange
        String token = "validToken";
        AdministrarDatos dato = new AdministrarDatos(); // Sin inicializar campos

        // Act
        ResponseEntity<?> response = administrarDatosController.creardato(token, dato);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Todos los campos obligatorios deben estar presentes", response.getBody());
        verify(administrarDatosService, never()).guardardato(any());
    }

    @Test
    void actualizardato_datoExistente_deberiaRetornarOk() {
        // Arrange
        String token = "validToken";
        AdministrarDatos dato = new AdministrarDatos();
        dato.setDocumento("123456");

        when(authbalan.authjwt(token)).thenReturn(true);
        when(administrarDatosService.actualizardato(dato)).thenReturn("Se actualizó correctamente");

        // Act
        ResponseEntity<String> response = administrarDatosController.actualizardato(token, dato);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se actualizó correctamente", response.getBody());
        verify(administrarDatosService).actualizardato(dato);
    }

    @Test
    void eliminardato_datoExistente_deberiaRetornarOk() {
        // Arrange
        String token = "validToken";
        String documento = "123456";

        when(authbalan.authjwt(token)).thenReturn(true);
        when(administrarDatosService.eliminardato(documento)).thenReturn("Dato eliminado correctamente");

        // Act
        ResponseEntity<String> response = administrarDatosController.eliminardato(token, documento);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dato eliminado correctamente", response.getBody());
        verify(administrarDatosService).eliminardato(documento);
    }

    @Test
    void eliminardato_datoNoExistente_deberiaRetornarNotFound() {
        // Arrange
        String token = "validToken";
        String documento = "123456";

        when(authbalan.authjwt(token)).thenReturn(true);
        when(administrarDatosService.eliminardato(documento)).thenReturn("El dato no existe");

        // Act
        ResponseEntity<String> response = administrarDatosController.eliminardato(token, documento);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El dato no existe", response.getBody());
        verify(administrarDatosService).eliminardato(documento);
    }
}
