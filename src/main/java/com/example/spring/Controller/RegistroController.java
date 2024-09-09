/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.Registro;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.registroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private registroService registroService;
    
    @Autowired
    JwtBalancer authbalan;

    @GetMapping // http://localhost:8080/api/registro
    public ResponseEntity<?> getAllRegistros(@RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                List<Registro> registros = registroService.getAllRegistros();
                return ResponseEntity.ok(registros);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
    
    @PostMapping("/nuevoregistro") // http://localhost:8080/api/registro/nuevoregistro
    public ResponseEntity<?> createRegistro(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @RequestBody Registro registro) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                Registro newRegistro = registroService.createRegistro(registro);
                return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}") // http://localhost:8080/api/registro/{id}
    public ResponseEntity<?> updateRegistro(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @PathVariable("id") Long id,
        @RequestBody Registro registro) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                Registro updatedRegistro = registroService.updateRegistro(id, registro);
                return ResponseEntity.ok(updatedRegistro);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no encontrado");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}") // http://localhost:8080/api/registro/{id}
    public ResponseEntity<String> deleteRegistro(
        @RequestHeader(value = "Authorization", required = false) String Token,
        @PathVariable("id") Long id) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                registroService.deleteRegistro(id);
                return ResponseEntity.ok("Registro eliminado correctamente");
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no encontrado");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
}
