package com.equipo.tallerproapp.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignTaskDTO {

    private Long taskId;
    private Long techId;

}
