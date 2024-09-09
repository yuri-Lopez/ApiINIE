/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Model.IngresoPersonal;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.IngresoPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingreso-personal")
public class IngresoPersonalController {

    @Autowired
    private IngresoPersonalService service;
    
    @Autowired
    JwtBalancer authbalan;

    @GetMapping // http://localhost:8080/api/ingreso-personal
    public ResponseEntity<?> getAllIngresoPersonals(@RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                List<IngresoPersonal> ingresoPersonals = service.findAll(); 
                return new ResponseEntity<>(ingresoPersonals, HttpStatus.OK);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PostMapping // http://localhost:8080/api/ingreso-personal
    public ResponseEntity<?> createIngresoPersonal(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @RequestBody IngresoPersonal ingresoPersonal) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                IngresoPersonal createdIngresoPersonal = service.save(ingresoPersonal);
                return new ResponseEntity<>(createdIngresoPersonal, HttpStatus.CREATED);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}") // http://localhost:8080/api/ingreso-personal/{id}
    public ResponseEntity<?> updateIngresoPersonal(
        @RequestHeader(value = "Authorization", required = false) String Token, 
        @PathVariable Long id,
        @RequestBody IngresoPersonal ingresoPersonal) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                if (!service.findById(id).isPresent()) {
                    return ResponseEntity.notFound().build();
                }
                ingresoPersonal.setId(id);
                IngresoPersonal updatedIngresoPersonal = service.save(ingresoPersonal);
                return ResponseEntity.ok(updatedIngresoPersonal);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}") // http://localhost:8080/api/ingreso-personal/{id}
    public ResponseEntity<String> deleteIngresoPersonal(
        @RequestHeader(value = "Authorization", required = false) String Token,
        @PathVariable Long id) {
        try {
            // Authenticate the token
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                if (!service.findById(id).isPresent()) {
                    return ResponseEntity.notFound().build();
                }
                service.deleteById(id);
                return ResponseEntity.noContent().build();
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
