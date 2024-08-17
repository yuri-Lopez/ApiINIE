/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author l
 */
@Entity
@Table(name = "libros")
@Data
public class Libros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "libro_id")
    private Integer libroId;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "a\u00f1o_publicacion")
    private Integer añoPublicacion;
    @Column(name = "genero")
    private String genero;
    
    
    @JoinColumn(name = "autor_id", referencedColumnName = "autor_id")
    @ManyToOne
    //recuerden que el json ignore lo usamos en donde esta la relacion muchos a muchos de una tabla y donde nos sale un gran cumulo de json de mala estructura
    @JsonIgnore
    private Autores autorId;
    @JoinColumn(name = "editorial_id", referencedColumnName = "editorial_id")
    @ManyToOne    
    //recuerden que el json ignore lo usamos en donde esta la relacion muchos a muchos de una tabla y donde nos sale un gran cumulo de json de mala estructura
    @JsonIgnore
    private Editoriales editorialId;

    
    
}
