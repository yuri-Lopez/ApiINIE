/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.example.spring.Service;

import com.example.spring.Model.EgresoPersonal;
import com.example.spring.Repository.EgresoPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class egresopersonalService {

    @Autowired
    private EgresoPersonalRepository egresoPersonalRepository;

    public List<EgresoPersonal> findAll() {
        return egresoPersonalRepository.findAll();
    }

    public Optional<EgresoPersonal> findById(Long id) {
        return egresoPersonalRepository.findById(id);
    }

    public EgresoPersonal save(EgresoPersonal egresoPersonal) {
        return egresoPersonalRepository.save(egresoPersonal);
    }

    public boolean existsById(Long id) {
        return egresoPersonalRepository.existsById(id);
    }

    public void deleteById(Long id) {
        egresoPersonalRepository.deleteById(id);
    }
}

