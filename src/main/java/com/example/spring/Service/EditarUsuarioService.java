/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.example.spring.Service;

import com.example.spring.Model.AdministrarDatos;
import com.example.spring.Model.EditarUsuario;
import com.example.spring.Repository.EditarUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditarUsuarioService {

    @Autowired
    private EditarUsuarioRepository editarUsuarioRepository;
    
    public List<EditarUsuario> traerUsuarios() {
        return editarUsuarioRepository.findAll();
    }
    
    public String guardarUsuario(EditarUsuario usuario) {
        try {
            editarUsuarioRepository.save(usuario);
            return "Guardado exitosamente";
        } catch (Exception e) {
            return "Ocurrió un error al guardar: " + e.getMessage();
        }
    }
    
    public String actualizarUsuario(EditarUsuario usuario) {
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
            editarUsuarioRepository.save(usuarioExistente);
            return "Se actualizó correctamente";
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

    public String actualizardato(AdministrarDatos dato) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    