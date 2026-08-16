package com.equipo.tallerproapp.controller;


import com.equipo.tallerproapp.dto.EditRoleDTO;
import com.equipo.tallerproapp.dto.UserProfileDTO;
import com.equipo.tallerproapp.dto.UsersDTO;
import com.equipo.tallerproapp.service.implementations.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.SingleResultAuthorizationManager.permitAll;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    //EDIT ROLE
    @PatchMapping("/edit_role/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserProfileDTO> editRole(
            @PathVariable Long id,
            @RequestBody EditRoleDTO role){
        return ResponseEntity.ok(usersService.editRole(id, role.getRole()));
    }

    //DEACTIVATE USER
    @PatchMapping("/deactivate_user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<UsersDTO> deactivateUser(@PathVariable Long id){
        return ResponseEntity.ok(usersService.deactivateUser(id));
    }

    //ACTIVATE USER
    @PatchMapping("/activate_user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<UsersDTO> activateUser(@PathVariable Long id){
        return ResponseEntity.ok(usersService.activateUser(id));
    }


}
