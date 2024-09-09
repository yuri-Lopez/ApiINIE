/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


package com.example.spring.Repository;

import com.example.spring.Model.AdministrarDatos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrarDatosRepository extends JpaRepository<AdministrarDatos, Long> {
    
    AdministrarDatos findByDocumento(String documento);

}