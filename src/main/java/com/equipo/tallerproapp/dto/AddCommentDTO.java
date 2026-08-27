package com.equipo.tallerproapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentDTO {

    @NotNull(message = "Task Id cannot be null")
    private Long task_id;

    @NotBlank(message = "Comment cannot be null")
    @Size(min = 10, max = 300, message = "Comment must be between 10 and 300 characters")
    private String comment;

}
