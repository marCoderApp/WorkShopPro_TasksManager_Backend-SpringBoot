package com.equipo.tallerproapp.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePriorityDTO {

    private Long task_id;
    private String priority;

}
