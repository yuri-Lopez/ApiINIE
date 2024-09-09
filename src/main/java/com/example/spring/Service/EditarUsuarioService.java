/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.example.spring.Service;


import com.example.spring.Model.EditarUsuario;
import com.example.spring.Repository.EditarUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class EditarUsuarioService {

    @Autowired
    private EditarUsuarioRepository editarUsuarioRepository;
    
    public List<EditarUsuario> traerUsuarios() {
        return editarUsuarioRepository.findAll();
    }
    
    public String guardarUsuario(EditarUsuario usuario) {
        // Validación previa de datos (si es necesario)
        if (usuario.getNombre() == null || usuario.getApellido() == null || 
            usuario.getDocumento() == null || usuario.getTelefono() == null ||
            usuario.getCorreoElectronico() == null || usuario.getPlanillaSeguridadSocial() == null) {
            return "Error: Todos los campos obligatorios deben estar presentes";
        }

        try {
            editarUsuarioRepository.save(usuario);
            return "Guardado exitosamente";
        } catch (DataIntegrityViolationException e) {
            // Maneja específicamente violaciones de integridad de datos
            return "Error de integridad de datos: " + e.getMessage();
        } catch (Exception e) {
            // Maneja otras excepciones
            return "Ocurrió un error al guardar: " + e.getMessage();
        }
    }

    
    public String actualizarUsuario(EditarUsuario usuario) {
        if (usuario == null || usuario.getDocumento() == null) {
            return "Error: Datos incompletos. El documento del usuario es obligatorio.";
        }

        // Buscar el usuario existente en la base de datos usando el documento
        EditarUsuario usuarioExistente = editarUsuarioRepository.findByDocumento(usuario.getDocumento());

        // Verificar si el usuario existe
        if (usuarioExistente != null) {
            // Actualizar los atributos del usuario existente
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setCorreoElectronico(usuario.getCorreoElectronico());
            usuarioExistente.setPlanillaSeguridadSocial(usuario.getPlanillaSeguridadSocial());

            // Guardar el usuario actualizado en la base de datos
            try {
                editarUsuarioRepository.save(usuarioExistente);
                return "Se actualizó correctamente";
            } catch (Exception e) {
                return "Error al guardar los datos: " + e.getMessage();
            }
        } else {
            return "El usuario no existe";
        }
    }
    public String eliminarUsuario(String documento) {
        // Buscar el usuario existente en la base de datos usando el documento
        EditarUsuario usuarioExistente = editarUsuarioRepository.findByDocumento(documento);

        // Verificar si el usuario existe
        if (usuarioExistente != null) {
            // Eliminar el usuario de la base de datos
            editarUsuarioRepository.delete(usuarioExistente);
            return "Usuario eliminado correctamente";
        } else {
            return "El usuario no existe";
        }
    }

    
}
    