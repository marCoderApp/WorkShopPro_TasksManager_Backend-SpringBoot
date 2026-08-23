package com.equipo.tallerproapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStatusDTO {

    private Long task_id;
    private String status;

}
