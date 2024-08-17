/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic; //si lo trae con javax cambiarlo a jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;




/**
 *
 * @author l
 */


//aca es muy importante poner el @Entity para indicar que esta clase de java sera nuestro MODELO o ENTIDAD
@Entity
//aca le ponemos el nombre la tablade la base de datos a donde va a estar unida nuestra clase
@Table(name = "autores")
//el @Data es para evitarnos hacer la encapsulacion nostros mismos, data lo hace automatico y esto viene con el plugin de loombok que instalamos en nuestro proyecto al crearlo
@Data
public class Autores implements Serializable {

    
    //esto esta indicando que es una primarykey, por eso pone el @Id
    private static final long serialVersionUID = 1L;
    @Id
    //esto indica que la primarykey es autoincrementable, por eso pone "IDENTITY"
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    //este es el nombre la columna de nuestra base de datos, tiene este nombre que ser IGUAL al que esta en nuestra base de datos
    @Column(name = "autor_id")
    //este es el nombre de nuestro campo en la clase java, no tiene por que ser exactamente igual al nombre de la base de datos pero se recomienda que sea el mismo pero con todo en minuscula
    private Integer autorId;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "biografia")
    private String biografia;
    @Basic(optional = false)
    @Column(name = "Documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "Activo")
    private boolean activo;
    
    //esta es una llave foranea que aunque no este en nuestra tabla de la base de datos, nos ayuda a obtener informacion de la relacion de esta tabla con otra tabla, para ver como funciona ingrese al endpoint //localhost:8081/autores/traerautores 
    @OneToMany(mappedBy = "autorId")
    private List<Libros> librosList;
    
    
    
    

    


}
