package com.equipo.tallerproapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditRoleDTO {

    @NotBlank(message = "Role cannot be blank!")
    private String role;

}
