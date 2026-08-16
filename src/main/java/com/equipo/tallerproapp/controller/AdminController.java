package com.equipo.tallerproapp.controller;


import com.equipo.tallerproapp.dto.UserProfileDTO;
import com.equipo.tallerproapp.dto.UsersDTO;
import com.equipo.tallerproapp.service.implementations.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<UsersDTO>> getAllUsers(){

        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping("/user_by/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(usersService.getUserById(id));
    }



}
