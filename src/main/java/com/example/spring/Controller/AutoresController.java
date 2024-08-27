/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Controller;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Dto.AutorespecificoDto;
import com.example.spring.Model.Autores;
import com.example.spring.Security.JwtBalancer;
import com.example.spring.Service.AutoresService;
import java.util.List;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author l
 */
//la anotacion rest controler se pone en todos las clases de controlador para indicarle a springboot que este sera uno de nuestros controladores
@RestController
//el cross origin se usa para que al momento de quere consumir nuestra api no nos de problemas de restricciones
@CrossOrigin("*")
//el requestmapping se usa para pedir una url base despues del "localhost:8081" lo pueden modificar dependiendo de sus modelos, si tienen el modelo usuarios podrian poner en lugar de donde esta "autores" la palabra "usuarios" y asi respectivamente
@RequestMapping("/autores/")
public class AutoresController {

    //el autowired es una inyeccion de dependencias, esto nos permite acceder a otras clases de otros paquetes en springboot, en este caso inyectamos el SERVICIO
    @Autowired
    AutoresService objautserv;

    @Autowired
    JwtBalancer authbalan;

    //este es un metodo basico que lo que hace es traerse todos los autores de la bd
    @GetMapping("traerautores") //localhost:8081/autores/traerautores
    public ResponseEntity<?> traerautores(@RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                List<Autores> autoreslist = objautserv.traerautores();
                return ResponseEntity.ok(autoreslist);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }
    }

    //este es un metodo en donde por defecto mostramos todo lo que tiene nuestro modelo, las relaciones y los campos que este tiene en nuestra clase de java
    @GetMapping("autorpordoc/{documento}") //localhost:8081/autores/autorpordoc/1098632598
    public ResponseEntity<?> traerunicoautor(@PathVariable("documento") String documento, @RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                Autores autor = objautserv.traerunicoautor(documento);
                return ResponseEntity.ok(autor);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }
    }

    //este es un metodo similar al anterior pero en el cual no le mostramos todos los campos de nuestra tabla o de nuestro modelo al usuario, creamos una clase hecha a nuestro gusto y necesidad(llamada en este caso AutorespecificoDto) y solo le mostramos lo que pongamos en esa clase, en este ejemplo no usamos el modelo directamente para mostrar
    @GetMapping("autorespecifico/{documento}") //localhost:8081/autores/autorpordoc/1098632598
    public ResponseEntity<?> autorespecifico(@PathVariable("documento") String documento, @RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                AutorespecificoDto autorespe = objautserv.traerespecifico(documento);
                return ResponseEntity.ok(autorespe);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }

    }

    //este es un metodo para crear un autor en la base de datos, como parametro le pedimos el modelo que queremos recibir, ojo, los nombres que le mande yo en el body tienen que ser los mismo nombres que aparecen en nuestro modelo
    @PostMapping("nuevoautor") //localhost:8081/autores/nuevoautor
    public ResponseEntity<?> Crearautor(@RequestBody Autores autor, @RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                String mensaje = objautserv.Guardarautor(autor);
                return ResponseEntity.ok(mensaje);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }
    }

//ejemplo del body que tenemos que mandar:
//    {
//        "nombre":"Carlos David Hernandez"
//        "biografia":"Escritor y poeta de la epoca contemporanea"
//        "documento":"10987654312"
//        "activo":true
//    }
    //aca tambien podrian usar el body que les deje en la parte de arriba (quiten los comentarios cuando lo pasen a postman)
    //este es un metodo para actualizar un autor en la base de datos, como parametro le pedimos el modelo que queremos recibir para actualizar, ojo, los nombres que le mande yo en el body tienen que ser los mismo nombres que aparecen en nuestro modelo
    //recuerden que aca no se hacen las validaciones de logica, si no que es en el servicio y comunmente en los parametrso recibimos el modelo o un dto como el que creamos llamado (AutorespecificoDto)
    @PutMapping("actualizarautor") //localhost:8081/autores/actualizarautor
    public ResponseEntity<?> actualizarautor(@RequestBody Autores autor,@RequestHeader(value = "Authorization", required = false) String Token) {
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                String mensaje = objautserv.ActualizarAutor(autor);
                return ResponseEntity.ok(mensaje);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }

    }

    //este es un metodo para eliminar un autor, es muy similar a el get en cuanto a lo que le pedimos, pero lo que hace esto es eliminar el registro, ojo, solo elimina registros de usuarios o autores que no esten en otras tablas 
    //ejemplo, si quiero borrar a un autor que ya esta unido a un campo de la tabla libros, no me va a dejar por que primero tendria que eliminar ese libro y despues si al autor,tengan en cuenta eso, es como en mysql para eliminar
    @DeleteMapping("eliminarautor/{documento}") //localhost:8081/autores/eliminarautor/1098423
    public ResponseEntity<?> eliminarautor(@PathVariable("documento") String documento,@RequestHeader(value = "Authorization", required = false) String Token) {
        
        try {
            Boolean res = authbalan.authjwt(Token);
            if (res) {
                String mensaje = objautserv.Eliminarautor(documento);
                return ResponseEntity.ok(mensaje);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Sin firmar");
            }
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.toString());
        }
      
    }

}
