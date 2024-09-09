/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.example.spring.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;  // Asegúrate de importar esta clase
import lombok.Data;

@Entity
@Table(name = "egreso_personal")
@Data
public class EgresoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Auto-incremented primary key

    @Basic(optional = false)
    @Column(name = "documento")
    private Long documento;  // Cambiado de Integer a Long

    @Basic(optional = false)
    @Column(name = "fecha_hora_ingreso")
    private LocalDateTime fechaHoraIngreso;  // Asegúrate de que la base de datos soporte LocalDateTime
}
