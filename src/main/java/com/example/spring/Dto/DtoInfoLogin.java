/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Dto;

import lombok.Data;

/**
 *
 * @author l
 */
@Data
public class DtoInfoLogin {

    private String token;

    private Usuario admininfo;


    @Data
    public static class Usuario{
        private String usuario;
        private String contraseña;
        
        
        
    }

}
