package com.equipo.tallerproapp.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditProfileDTO {

    private String name;
    private String lastname;
    private String dni;

}
