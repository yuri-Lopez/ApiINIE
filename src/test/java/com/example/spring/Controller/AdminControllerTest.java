/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Dto.DtoInfoLogin;
import com.example.spring.Dto.Dtologin;
import com.example.spring.Service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    private Dtologin dtologin;
    private DtoInfoLogin dtoInfoLogin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dtologin = new Dtologin();
        dtologin.setUsuario("admin");
        dtologin.setContraseña("password");

        dtoInfoLogin = new DtoInfoLogin();
        dtoInfoLogin.setToken("mockedToken");
    }

    @Test
    public void testLoginSuccess() {
        when(adminService.login(dtologin)).thenReturn(dtoInfoLogin);

        ResponseEntity<?> response = adminController.login(dtologin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtoInfoLogin, response.getBody());
    }

    @Test
    public void testLoginInvalidUser() {
        when(adminService.login(dtologin)).thenThrow(new CustomException(HttpStatus.NOT_FOUND.value(), "Tu usuario no existe en el sistema"));

        ResponseEntity<?> response = adminController.login(dtologin);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Tu usuario no existe en el sistema"));
    }

    @Test
    public void testLoginWrongPassword() {
        when(adminService.login(dtologin)).thenThrow(new CustomException(HttpStatus.BAD_REQUEST.value(), "tu contraseña es incorrecta"));

        ResponseEntity<?> response = adminController.login(dtologin);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("tu contraseña es incorrecta"));
    }
}
