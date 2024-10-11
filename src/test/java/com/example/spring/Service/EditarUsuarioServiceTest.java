/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.Model.EditarUsuario;
import com.example.spring.Repository.EditarUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditarUsuarioServiceTest {

    @InjectMocks
    private EditarUsuarioService editarUsuarioService;

    @Mock
    private EditarUsuarioRepository editarUsuarioRepository;

    private EditarUsuario usuario;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.initMocks(this);

        // Configurar el objeto usuario
        usuario = new EditarUsuario();
        usuario.setNombre("Marcos");
        usuario.setApellido("Cortez");
        usuario.setDocumento("75432189");
        usuario.setTelefono("313456789");
        usuario.setCorreoElectronico("marcosc@hotmail.com");
        usuario.setPlanillaSeguridadSocial("56");
    }

    @Test
    void traerUsuarios_ReturnsAllUsers() {
        when(editarUsuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

        List<EditarUsuario> usuarios = editarUsuarioService.traerUsuarios();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Marcos", usuarios.get(0).getNombre());
    }

    @Test
    void guardarUsuario_ValidData_ReturnsSuccessMessage() {
        when(editarUsuarioRepository.save(any(EditarUsuario.class))).thenReturn(usuario);

        String result = editarUsuarioService.guardarUsuario(usuario);

        assertEquals("Guardado exitosamente", result);
        verify(editarUsuarioRepository).save(usuario);
    }

    @Test
    void guardarUsuario_MissingFields_ReturnsErrorMessage() {
        EditarUsuario invalidUsuario = new EditarUsuario(); // Sin datos

        String result = editarUsuarioService.guardarUsuario(invalidUsuario);

        assertEquals("Error: Todos los campos obligatorios deben estar presentes", result);
        verify(editarUsuarioRepository, never()).save(any(EditarUsuario.class));
    }

    @Test
    void actualizarUsuario_ExistingUser_ReturnsUpdateMessage() {
        when(editarUsuarioRepository.findByDocumento(usuario.getDocumento())).thenReturn(usuario);

        String result = editarUsuarioService.actualizarUsuario(usuario);

        assertEquals("Se actualizó correctamente", result);
        verify(editarUsuarioRepository).save(usuario);
    }

    @Test
    void actualizarUsuario_NonExistingUser_ReturnsNotFoundMessage() {
        when(editarUsuarioRepository.findByDocumento(usuario.getDocumento())).thenReturn(null);

        String result = editarUsuarioService.actualizarUsuario(usuario);

        assertEquals("El usuario no existe", result);
    }

    @Test
    void eliminarUsuario_ExistingUser_ReturnsDeletedMessage() {
        when(editarUsuarioRepository.findByDocumento(usuario.getDocumento())).thenReturn(usuario);

        String result = editarUsuarioService.eliminarUsuario(usuario.getDocumento());

        assertEquals("Usuario eliminado correctamente", result);
        verify(editarUsuarioRepository).delete(usuario);
    }

    @Test
    void eliminarUsuario_NonExistingUser_ReturnsNotFoundMessage() {
        when(editarUsuarioRepository.findByDocumento(usuario.getDocumento())).thenReturn(null);

        String result = editarUsuarioService.eliminarUsuario(usuario.getDocumento());

        assertEquals("El usuario no existe", result);
    }
}
