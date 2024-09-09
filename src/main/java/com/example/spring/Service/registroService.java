/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.example.spring.Service;

import com.example.spring.Model.Registro;
import com.example.spring.Repository.registroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class  registroService {

    final registroRepository registroRepository;

    @Autowired
    public registroService(registroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public List<Registro> getAllRegistros() {
        return registroRepository.findAll();
    }

    public Optional<Registro> getRegistroById(Long id) {
        return registroRepository.findById(id);
    }

    public Registro createRegistro(Registro registro) {
        return registroRepository.save(registro);
    }

    public Registro updateRegistro(Long id, Registro registro) {
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("Registro not found with id: " + id);
        }
        registro.setId(id); // Ensure the ID matches
        return registroRepository.save(registro);
    }

    public void deleteRegistro(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("Registro not found with id: " + id);
        }
        registroRepository.deleteById(id);
    }
}
