/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.Registro;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.registroService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RegistroControllerTest {

    @InjectMocks
    private RegistroController registroController;

    @Mock
    private registroService registroService;

    @Mock
    private JwtBalancer authbalan;

    private Registro registro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registro = new Registro();
        // Asignar valores a `registro` si es necesario
    }

    @Test
    void getAllRegistros_ValidToken_ReturnsRegistros() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(registroService.getAllRegistros()).thenReturn(Collections.singletonList(registro));

        ResponseEntity<?> response = registroController.getAllRegistros("valid_token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
    }

    @Test
    void getAllRegistros_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<?> response = registroController.getAllRegistros("invalid_token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sin firmar"));
    }

    @Test
    void createRegistro_ValidToken_ReturnsCreatedRegistro() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(registroService.createRegistro(any(Registro.class))).thenReturn(registro);

        ResponseEntity<?> response = registroController.createRegistro("valid_token", registro);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(registro, response.getBody());
    }

    @Test
    void createRegistro_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<?> response = registroController.createRegistro("invalid_token", registro);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sin firmar"));
    }

    @Test
    void updateRegistro_ValidTokenAndExistingRegistro_ReturnsUpdatedRegistro() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(registroService.updateRegistro(anyLong(), any(Registro.class))).thenReturn(registro);

        ResponseEntity<?> response = registroController.updateRegistro("valid_token", 1L, registro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registro, response.getBody());
    }

    @Test
    void updateRegistro_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<?> response = registroController.updateRegistro("invalid_token", 1L, registro);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sin firmar"));
    }

    @Test
    void deleteRegistro_ValidToken_ReturnsDeletedMessage() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);

        ResponseEntity<String> response = registroController.deleteRegistro("valid_token", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro eliminado correctamente", response.getBody());
    }

    @Test
    void deleteRegistro_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<String> response = registroController.deleteRegistro("invalid_token", 1L);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sin firmar"));
    }
}
