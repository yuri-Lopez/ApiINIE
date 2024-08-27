/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Dto.AutorespecificoDto;
import com.example.spring.Model.Autores;
import com.example.spring.Repository.AutoresRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author l
 */
//tenemos que poner siempre la anotacion @Service en un servicio para indicarle a springboot que este sera una clase de servicio
//aca en el servicio es donde manjeamos toda nuestra logica de negocio, ponemos validaciones y retornamos mensajes
@Service
public class AutoresService {

    //el autowired es una inyeccion de dependencias, esto nos permite acceder a otras clases de otros paquetes en springboot,en este caso intectamos el REPOSITORIO
    @Autowired
    AutoresRepository objautrep;

    public List<Autores> traerautores() {
        return objautrep.findAll();
    }

    public Autores traerunicoautor(String documento) {
        return objautrep.traerautor(documento);
    }
    
    
    //aca mankjamos nuestro dto y tomamos un autor, lo guardamos en el modelo autores y luego vamos poniendo la informacion que nos traemos en nuestro dto
    public AutorespecificoDto traerespecifico(String documento){
        Autores autores = objautrep.traerautor(documento);
        AutorespecificoDto dto = new AutorespecificoDto();
        dto.setDocumento(autores.getDocumento()); //aca por ejemplo pasamos el numero de documento de autores a el dto AutorespecificoDto
        dto.setNombre(autores.getNombre());
        dto.setBiografia(autores.getBiografia());
        dto.setActivo(autores.isActivo());
        return dto; //y retornamos la misma clase que ponemos en el metodo
    }

    //aca usamos el metodo predefinido de springboot para guardar llamado save()
    public String Guardarautor(Autores aut) {
        Autores autor = objautrep.traerautor(aut.getDocumento());
        if (autor == null) {
            objautrep.save(aut);
            return "Guardado exitosamente";
        } else {
            throw new CustomException(HttpStatus.CONFLICT.value(), "el autor ya existe en la bd");
        }
    }
    
    //aca usamos el metodo predefinido de springboot para guardar llamado save() pero con una sola diferencia al save anterior, que esta vez no estamos guardando un autor desde cero si no que lo estamos actualizando
    public String ActualizarAutor(Autores aut){
        Autores autor = objautrep.traerautor(aut.getDocumento());
        if (autor != null) {
             autor.setNombre(aut.getNombre());  //le seteamos a el autor antiguo, los datos nuevos que el nos mando para despues llamar a el .save() y guardar cambios
             autor.setBiografia(aut.getBiografia());
             autor.setActivo(aut.isActivo());
             objautrep.save(autor);
             return "Se actualizo exitosamente";
        }else {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "el autor no existe en la bd");
        }
    }
    
    
    
    //para eliminar usamos el metodo ya definido en springboot llamada deletebyid y le podemos pasar unicamente el id del autor o del campo a eliminar
    public String Eliminarautor(String Documento){
        Autores autor = objautrep.traerautor(Documento);
        if (autor != null) {
             objautrep.deleteById(autor.getAutorId());
             return "Se elimino correctamente";
        }else {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "el autor no existe en la bd");
        }
    }
    
    
    
   

    
    

}
