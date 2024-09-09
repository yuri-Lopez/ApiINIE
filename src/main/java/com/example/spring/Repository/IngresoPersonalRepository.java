/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.spring.Repository;

import com.example.spring.Model.IngresoPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LOPEZ YURI
 */

@Repository
public interface IngresoPersonalRepository extends JpaRepository<IngresoPersonal, Long> {
}
