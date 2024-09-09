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
//el @Data es para evitarnos hacer la encapsulacion nostros mismos, data lo hace automatico y esto viene con el plugin de loombok que instalamos en nuestro proyecto al crearlo
@Data
public class AutorespecificoDto {
    private String nombre;
    private String biografia;
    private String documento;
    private boolean activo;
}
