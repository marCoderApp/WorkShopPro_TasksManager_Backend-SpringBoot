package com.equipo.tallerproapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private String name;
    private String lastname;
    private String dni;
    private String email;
    private String role;
    private Boolean isEnabled;

    private List<TaskDTO> tasks;

}
