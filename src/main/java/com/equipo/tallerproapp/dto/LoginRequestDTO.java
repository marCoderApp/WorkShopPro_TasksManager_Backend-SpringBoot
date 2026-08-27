package com.equipo.tallerproapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @Email(message = "Please enter a valid email address!")
    @NotBlank(message = "Email cannot be blank!")
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    private String password;

}
