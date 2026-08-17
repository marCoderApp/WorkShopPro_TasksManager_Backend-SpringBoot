package com.equipo.tallerproapp.controller;


import com.equipo.tallerproapp.dto.*;
import com.equipo.tallerproapp.service.implementations.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    //TO SEE OWN PROFILE
    @GetMapping("/my_profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OwnProfileDTO> toSeeOwnProfile(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.toSeeOwnProfile());
    }

    //TO EDIT OWN PROFILE
    @PatchMapping("/edit_profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OwnProfileDTO> editProfile(@RequestBody EditProfileDTO dto){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.editProfile(dto));

    }

}
