package com.equipo.tallerproapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStatusDTO {

    @NotBlank(message = "Task Id cannot be null")
    private Long task_id;

    @NotBlank(message = "Status cannot be null")
    private String status;

}
