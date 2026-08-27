package com.equipo.tallerproapp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePriorityDTO {

    @NotBlank(message = "Task Id cannot be null")
    private Long task_id;

    @NotBlank(message = "Priority cannot be null")
    private String priority;

}
