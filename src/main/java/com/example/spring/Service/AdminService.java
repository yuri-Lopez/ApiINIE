/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.spring.Service;

import com.example.spring.CustomException.CustomException;
import com.example.spring.Dto.DtoInfoLogin;
import com.example.spring.Dto.Dtologin;
import com.example.spring.Model.Admin;
import com.example.spring.Repository.AdminRepository;
import com.example.spring.Security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author l
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository objadmrep;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public DtoInfoLogin login(Dtologin log) {
        Admin modeladm = objadmrep.findByUsuario(log.getUsuario());
        if (modeladm != null) {
            if (modeladm.getContrasena().equals(log.getContraseña())) {
                String Token = jwtTokenUtil.generateToken(log.getUsuario());
                DtoInfoLogin responselogin = new DtoInfoLogin();
                DtoInfoLogin.Usuario userinfo = new DtoInfoLogin.Usuario();
                userinfo.setUsuario(modeladm.getUsuario());
                userinfo.setContraseña(modeladm.getContrasena());
                responselogin.setToken(Token);
                responselogin.setAdmininfo(userinfo);
                return responselogin;
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST.value(), "tu contraseña es incorrecta");
            }
        } else {
                throw new CustomException(HttpStatus.NOT_FOUND.value(),"Tu usuario no existe en el sistema");
        }
    }

}
