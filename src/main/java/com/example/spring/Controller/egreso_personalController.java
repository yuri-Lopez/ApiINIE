/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.EgresoPersonal;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.egresopersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/egreso-personal")
public class egreso_personalController {

    @Autowired
    private egresopersonalService egresoPersonalService;
    
    @Autowired
    JwtBalancer authbalan;

    // Obtener todos los registros
    @GetMapping //http://localhost:8080/api/egreso-personal
    public ResponseEntity<?> getAllEgresoPersonales(@RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                List<EgresoPersonal> egresoPersonales = egresoPersonalService.findAll();
                return new ResponseEntity<>(egresoPersonales, HttpStatus.OK);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    // Crear un nuevo registro
    @PostMapping //http://localhost:8080/api/egreso-personal
    public ResponseEntity<?> createEgresoPersonal(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @RequestBody EgresoPersonal egresoPersonal) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                EgresoPersonal createdEgresoPersonal = egresoPersonalService.save(egresoPersonal);
                return new ResponseEntity<>(createdEgresoPersonal, HttpStatus.CREATED);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    // Actualizar un registro existente
    @PutMapping("/{id}") //http://localhost:8080/api/egreso-personal/{id}
    public ResponseEntity<?> updateEgresoPersonal(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @PathVariable("id") Long id,
        @RequestBody EgresoPersonal egresoPersonal) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                if (!egresoPersonalService.existsById(id)) {
                    return ResponseEntity.notFound().build();
                }
                egresoPersonal.setId(id);
                EgresoPersonal updatedEgresoPersonal = egresoPersonalService.save(egresoPersonal);
                return new ResponseEntity<>(updatedEgresoPersonal, HttpStatus.OK);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    // Eliminar un registro por ID
    @DeleteMapping("/{id}") //http://localhost:8080/api/egreso-personal/{id}
    public ResponseEntity<String> deleteEgresoPersonal(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @PathVariable("id") Long id) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                if (!egresoPersonalService.existsById(id)) {
                    return ResponseEntity.notFound().build();
                }
                egresoPersonalService.deleteById(id);
                return ResponseEntity.ok("Registro eliminado correctamente");
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
}
