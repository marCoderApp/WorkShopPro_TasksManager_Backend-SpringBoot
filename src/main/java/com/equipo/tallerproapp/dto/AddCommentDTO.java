package com.equipo.tallerproapp.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentDTO {

    private Long task_id;
    private String comment;

}
