/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.spring.Repository;

import com.example.spring.Model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author l
 */
//tenemos que poner el @Repository para indicarle a springboot que esta INTERFACE actuara como un repositorio ojo,todos los repositorios son INTERFACES no son clases de java normales
@Repository
//tenemos que poner el extends JpaRepository<aca ponemos el modelo que vayamos a trabajar, y aca ponemos el tipo de dato que es la llave primaria>
public interface AutoresRepository extends JpaRepository<Autores, Integer>{
    
    //el ?1 quiere decir primer parametro
    //recuerden tambine poner siempre el nativequery en true
    @Query(value = "Select * from autores where documento= ?1",nativeQuery = true)
    Autores traerautor(String documento);
    
    
    
}
