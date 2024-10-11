/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Dto.DtoInfoLogin;
import com.example.spring.Dto.Dtologin;
import com.example.spring.Model.Admin;
import com.example.spring.Repository.AdminRepository;
import com.example.spring.Security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private Dtologin dtologin;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dtologin = new Dtologin();
        dtologin.setUsuario("admin");
        dtologin.setContraseña("password");

        admin = new Admin();
        admin.setUsuario("admin");
        admin.setContrasena("password");
    }

    @Test
    public void testLoginSuccess() {
        when(adminRepository.findByUsuario("admin")).thenReturn(admin);
        when(jwtTokenUtil.generateToken("admin")).thenReturn("mockedToken");

        DtoInfoLogin result = adminService.login(dtologin);

        assertNotNull(result);
        assertEquals("mockedToken", result.getToken());
        assertEquals("admin", result.getAdmininfo().getUsuario());
    }

    @Test
    public void testLoginInvalidUser() {
        when(adminRepository.findByUsuario("admin")).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> adminService.login(dtologin));

        assertEquals("Tu usuario no existe en el sistema", exception.getMessage());
    }

    @Test
    public void testLoginWrongPassword() {
        admin.setContrasena("wrongPassword");
        when(adminRepository.findByUsuario("admin")).thenReturn(admin);

        CustomException exception = assertThrows(CustomException.class, () -> adminService.login(dtologin));

        assertEquals("tu contraseña es incorrecta", exception.getMessage());
    }
}
