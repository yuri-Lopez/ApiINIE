/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.EditarUsuario;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.EditarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/editar_usuario")
public class EditarUsuarioController {
    
    @Autowired
    private EditarUsuarioService editarUsuarioService;
    
    @Autowired
    JwtBalancer authbalan;
    
    @GetMapping("/traerdato") // http://localhost:8080/editarusuario/traerdato
    public ResponseEntity<?> traerdato(@RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
        List<EditarUsuario> datos = editarUsuarioService.traerUsuarios();
        return ResponseEntity.ok(datos);
    }else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }
    }
    
    @PostMapping("/nuevodato")
    public ResponseEntity<?> creardato(@RequestHeader(value = "Authorization", required = false) String Token, @RequestBody EditarUsuario dato) {
    // Validar datos
    if (dato == null || dato.getNombre() == null || dato.getApellido() == null ||
        dato.getDocumento() == null || dato.getTelefono() == null ||
        dato.getCorreoElectronico() == null || dato.getPlanillaSeguridadSocial() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Todos los campos obligatorios deben estar presentes");
    }
    
    try {
        Boolean res = authbalan.authjwt(Token);
        if (res) {
            String mensaje = editarUsuarioService.guardarUsuario(dato);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
        }
    } catch (CustomException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error: " + ex.getMessage());
    }
}

    
    @PutMapping("/actualizardato")
    public ResponseEntity<String> actualizardato(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody EditarUsuario usuario) {
        // Verifica que el token sea válido y realiza la actualización
        Boolean isAuthenticated = authbalan.authjwt(token);
        if (isAuthenticated) {
            String mensaje = editarUsuarioService.actualizarUsuario(usuario);
            if ("Se actualizó correctamente".equals(mensaje)) {
                return ResponseEntity.ok(mensaje);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sin firmar");
        }
    }
    
    @DeleteMapping("/eliminar/{documento}") // http://localhost:8080/editarusuario/eliminar/{documento}
    public ResponseEntity<String> eliminardato(
        @RequestHeader(value = "Authorization", required = false) String token,
        @PathVariable("documento") String documento) {
    try {
        // Verificar el token
        Boolean res = authbalan.authjwt(token);
        if (res) {
            // Eliminar el usuario
            String mensaje = editarUsuarioService.eliminarUsuario(documento);
            if ("Usuario eliminado correctamente".equals(mensaje)) {
                return ResponseEntity.ok(mensaje);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }
        } else {
            // Token inválido
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
        }
    } catch (CustomException ex) {
        // Manejo de excepciones personalizadas
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    } catch (Exception ex) {
        // Manejo de excepciones generales
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
}