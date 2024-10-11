/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.IngresoPersonal;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.IngresoPersonalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngresoPersonalControllerTest {

    @InjectMocks
    private IngresoPersonalController ingresoPersonalController;

    @Mock
    private IngresoPersonalService service;

    @Mock
    private JwtBalancer authbalan;

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
    void getAllIngresoPersonals_ReturnsOk() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(service.findAll()).thenReturn(Arrays.asList(ingresoPersonal));

        ResponseEntity<?> response = ingresoPersonalController.getAllIngresoPersonals("token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<IngresoPersonal>) response.getBody()).size());
    }

    @Test
    void getAllIngresoPersonals_Unauthorized() {
        when(authbalan.authjwt(anyString())).thenReturn(false);

        ResponseEntity<?> response = ingresoPersonalController.getAllIngresoPersonals("token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void createIngresoPersonal_ReturnsCreated() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(service.save(any(IngresoPersonal.class))).thenReturn(ingresoPersonal);

        ResponseEntity<?> response = ingresoPersonalController.createIngresoPersonal("token", ingresoPersonal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ingresoPersonal, response.getBody());
    }

    @Test
    void createIngresoPersonal_Unauthorized() {
        when(authbalan.authjwt(anyString())).thenReturn(false);

        ResponseEntity<?> response = ingresoPersonalController.createIngresoPersonal("token", ingresoPersonal);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void updateIngresoPersonal_ReturnsOk() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(service.findById(1L)).thenReturn(Optional.of(ingresoPersonal));
        when(service.save(any(IngresoPersonal.class))).thenReturn(ingresoPersonal);

        ResponseEntity<?> response = ingresoPersonalController.updateIngresoPersonal("token", 1L, ingresoPersonal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingresoPersonal, response.getBody());
    }

    @Test
    void updateIngresoPersonal_NotFound() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = ingresoPersonalController.updateIngresoPersonal("token", 1L, ingresoPersonal);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteIngresoPersonal_ReturnsNoContent() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(service.findById(1L)).thenReturn(Optional.of(ingresoPersonal));

        ResponseEntity<String> response = ingresoPersonalController.deleteIngresoPersonal("token", 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteIngresoPersonal_NotFound() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = ingresoPersonalController.deleteIngresoPersonal("token", 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteIngresoPersonal_Unauthorized() {
        when(authbalan.authjwt(anyString())).thenReturn(false);

        ResponseEntity<String> response = ingresoPersonalController.deleteIngresoPersonal("token", 1L);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
