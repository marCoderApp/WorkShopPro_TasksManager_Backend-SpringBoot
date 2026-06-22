package com.equipo.tallerproapp.dto;

import lombok.Data;
import com.equipo.tallerproapp.model.Role;

@Data
public class RegisterRequestDTO {

    private String name;
    private String lastName;
    private String dni;
    private String email;
    private String password;
    private Role role;

}
