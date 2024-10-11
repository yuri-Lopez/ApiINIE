/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author l
 */
@Data
@AllArgsConstructor // Constructor que acepta todos los parámetros
@NoArgsConstructor  // Constructor por defecto
public class DtoInfoLogin {

    private String token;
    private Usuario admininfo;

    @Data
    @AllArgsConstructor // Constructor que acepta todos los parámetros para Usuario
    @NoArgsConstructor  // Constructor por defecto para Usuario
    public static class Usuario {
        private String usuario;
        private String contraseña;
    }
}
