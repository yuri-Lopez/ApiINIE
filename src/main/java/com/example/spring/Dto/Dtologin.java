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
public class Dtologin {
    
    private String usuario;
    private String contraseña;
}
