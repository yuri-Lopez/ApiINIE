/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.EgresoPersonal;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.egresopersonalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class egreso_personalControllerTest {

    @InjectMocks
    private egreso_personalController egresoPersonalController;

    @Mock
    private egresopersonalService egresoPersonalService;

    @Mock
    private JwtBalancer authbalan;

    private EgresoPersonal egresoPersonal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        egresoPersonal = new EgresoPersonal();
        egresoPersonal.setId(1L);
        egresoPersonal.setDocumento(123456L);
        egresoPersonal.setFechaHoraIngreso(LocalDateTime.now());
    }

    @Test
    void testGetAllegreso_personales_Success() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(egresoPersonalService.findAll()).thenReturn(Arrays.asList(egresoPersonal));

        ResponseEntity<?> response = egresoPersonalController.getAllEgresoPersonales("token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((Iterable<?>) response.getBody()).iterator().hasNext());
    }

    @Test
    void testCreateegreso_personal_Success() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(egresoPersonalService.save(any(EgresoPersonal.class))).thenReturn(egresoPersonal);

        ResponseEntity<?> response = egresoPersonalController.createEgresoPersonal("token", egresoPersonal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(egresoPersonal, response.getBody());
    }

    @Test
    void testUpdateegreso_personal_Success() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(egresoPersonalService.existsById(1L)).thenReturn(true);
        when(egresoPersonalService.save(any(EgresoPersonal.class))).thenReturn(egresoPersonal);

        ResponseEntity<?> response = egresoPersonalController.updateEgresoPersonal("token", 1L, egresoPersonal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(egresoPersonal, response.getBody());
    }

    @Test
    void testDeleteegreso_personal_Success() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(egresoPersonalService.existsById(1L)).thenReturn(true);

        ResponseEntity<String> response = egresoPersonalController.deleteEgresoPersonal("token", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro eliminado correctamente", response.getBody());
        verify(egresoPersonalService).deleteById(1L);
    }

    @Test
    void testGetAllegreso_personal_Unauthorized() {
        when(authbalan.authjwt(anyString())).thenReturn(false);

        ResponseEntity<?> response = egresoPersonalController.getAllEgresoPersonales("token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCreateegreso_personal_Error() {
        when(authbalan.authjwt(anyString())).thenReturn(true);
        when(egresoPersonalService.save(any(EgresoPersonal.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = egresoPersonalController.createEgresoPersonal("token", egresoPersonal);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
