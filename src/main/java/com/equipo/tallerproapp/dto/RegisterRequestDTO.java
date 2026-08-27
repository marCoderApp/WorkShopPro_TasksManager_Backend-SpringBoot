package com.equipo.tallerproapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.equipo.tallerproapp.model.Role;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Name cannot be blank!")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
    private String name;

    @NotBlank(message = "Last name cannot be blank!")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters!")
    private String lastName;

    private String dni;

    @NotBlank(message = "Email cannot be blank!")
   @Email(message = "Email must be valid!")
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, max = 20, message = "Password must be between 6 and 20 characters!")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$"
            , message = "Password must contain at least one uppercase letter, one number, and one special character."
    )
    private String password;

    @NotNull(message = "Role cannot be null!")
    private Role role;

}
