/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.spring.Service;

import com.example.spring.Model.AdministrarDatos;
import com.example.spring.Repository.AdministrarDatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrarDatosService {

    @Autowired
    private AdministrarDatosRepository administrarDatosRepository;
    
    public List<AdministrarDatos> traerdato() {
        return administrarDatosRepository.findAll();
    }
    
    public String guardardato(AdministrarDatos dato) {
        try {
            administrarDatosRepository.save(dato);
            return "Guardado exitosamente";
        } catch (Exception e) {
            return "Ocurrió un error al guardar: " + e.getMessage();
        }
    }
    
    public String actualizardato(AdministrarDatos dato) {
        // Buscar el dato existente en la base de datos usando el documento
        AdministrarDatos datoExistente = administrarDatosRepository.findByDocumento(dato.getDocumento());

        // Verificar si el dato existe
        if (datoExistente != null) {
            // Actualizar los atributos del empleado existente
            datoExistente.setNombre(dato.getNombre());
            datoExistente.setApellido(dato.getApellido());
            datoExistente.setTelefono(dato.getTelefono());
            datoExistente.setCorreoElectronico(dato.getCorreoElectronico());
            datoExistente.setPlanillaSeguridadSocial(dato.getPlanillaSeguridadSocial());
            // Guardar el dato actualizado en la base de datos
            administrarDatosRepository.save(datoExistente);
            return "Se actualizó correctamente";
        } else {
            return "El dato no existe";
        }
    }
    
    public String eliminardato(String documento) {
    // Buscar el dato existente en la base de datos usando el documento
    AdministrarDatos datoExistente = administrarDatosRepository.findByDocumento(documento);

    // Verificar si el dato existe
    if (datoExistente != null) {
        // Eliminar el dato de la base de datos
        administrarDatosRepository.delete(datoExistente);
        return "Dato eliminado correctamente";
    } else {
        return "El dato no existe";
    }
    }
}
