/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.EditarUsuario;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.EditarUsuarioService;
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
import static org.mockito.Mockito.*;

class EditarUsuarioControllerTest {

    @InjectMocks
    private EditarUsuarioController editarUsuarioController;

    @Mock
    private EditarUsuarioService editarUsuarioService;

    @Mock
    private JwtBalancer authbalan;

    private EditarUsuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new EditarUsuario();
        usuario.setNombre("Marcos");
        usuario.setApellido("Cortez");
        usuario.setDocumento("75432189");
        usuario.setTelefono("313456789");
        usuario.setCorreoElectronico("marcosc@hotmail.com");
        usuario.setPlanillaSeguridadSocial("56");
    }

    @Test
    void traerdato_ValidToken_ReturnsUsers() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(editarUsuarioService.traerUsuarios()).thenReturn(Collections.singletonList(usuario));

        ResponseEntity<?> response = editarUsuarioController.traerdato("valid_token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
    }

    @Test
    void traerdato_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<?> response = editarUsuarioController.traerdato("invalid_token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sin firmar"));
    }

    @Test
    void creardato_ValidData_ReturnsCreatedMessage() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(editarUsuarioService.guardarUsuario(any(EditarUsuario.class))).thenReturn("Guardado exitosamente");

        ResponseEntity<?> response = editarUsuarioController.creardato("valid_token", usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Guardado exitosamente", response.getBody());
    }

    @Test
    void creardato_MissingFields_ReturnsBadRequest() {
        EditarUsuario invalidUsuario = new EditarUsuario(); // Sin datos

        ResponseEntity<?> response = editarUsuarioController.creardato("valid_token", invalidUsuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Todos los campos obligatorios deben estar presentes", response.getBody());
    }

    @Test
    void actualizardato_ValidTokenAndExistingUser_ReturnsUpdateMessage() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(editarUsuarioService.actualizarUsuario(any(EditarUsuario.class))).thenReturn("Se actualizó correctamente");

        ResponseEntity<String> response = editarUsuarioController.actualizardato("valid_token", usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se actualizó correctamente", response.getBody());
    }

    @Test
    void actualizardato_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<String> response = editarUsuarioController.actualizardato("invalid_token", usuario);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Sin firmar", response.getBody());
    }

    @Test
    void eliminardato_ExistingUser_ReturnsDeletedMessage() {
        when(authbalan.authjwt(any(String.class))).thenReturn(true);
        when(editarUsuarioService.eliminarUsuario(any(String.class))).thenReturn("Usuario eliminado correctamente");

        ResponseEntity<String> response = editarUsuarioController.eliminardato("valid_token", usuario.getDocumento());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario eliminado correctamente", response.getBody());
    }

    @Test
    void eliminardato_InvalidToken_ReturnsUnauthorized() {
        when(authbalan.authjwt(any(String.class))).thenReturn(false);

        ResponseEntity<String> response = editarUsuarioController.eliminardato("invalid_token", usuario.getDocumento());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Sin firmar", response.getBody());
    }
}
