/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.spring.Service;

import com.example.spring.Model.IngresoPersonal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngresoPersonalService {

    private final JpaRepository<IngresoPersonal, Long> repository;

    public IngresoPersonalService(JpaRepository<IngresoPersonal, Long> repository) {
        this.repository = repository;
    }

    public List<IngresoPersonal> findAll() {
        return repository.findAll();
    }

    public Optional<IngresoPersonal> findById(Long id) {
        return repository.findById(id);
    }

    public IngresoPersonal save(IngresoPersonal ingresoPersonal) {
        return repository.save(ingresoPersonal);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
