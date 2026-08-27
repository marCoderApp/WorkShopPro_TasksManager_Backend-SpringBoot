package com.equipo.tallerproapp.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignTaskDTO {

    @NotNull(message = "Task Id cannot be null")
    private Long taskId;

    @NotNull(message = "Tech id cannot be null")
    private Long techId;

}
