package com.equipo.tallerproapp.dto;


import com.equipo.tallerproapp.enums.PriorityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

  private Long id;
    private String title;
     private String description;
     private String status;
     private LocalDateTime createdAt;
     private LocalDateTime updatedAt;
     private String createdBy;
     private String updatedBy;
     private PriorityEnum priority;
     private LocalDateTime due_date;
     private String category;
     private String comment;
     private String assignedTo;

}
