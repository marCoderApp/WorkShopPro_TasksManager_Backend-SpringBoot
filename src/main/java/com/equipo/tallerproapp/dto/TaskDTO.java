package com.equipo.tallerproapp.dto;


import com.equipo.tallerproapp.enums.PriorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
  @NotBlank(message = "Title cannot be null")
    private String title;

  @NotBlank(message = "Description cannot be null")
     private String description;

  @NotBlank(message = "Status cannot be null")
     private String status;

     private LocalDateTime createdAt;
     private LocalDateTime updatedAt;

     @NotBlank(message = "User id cannot be null")
     private String createdBy;
     private String updatedBy;

     @NotNull(message = "Priority cannot be null")
     private PriorityEnum priority;

     @NotNull(message = "Due date cannot be null")
     private LocalDateTime due_date;

     @NotBlank(message = "Category cannot be null")
     private String category;

     private String comment;

     @NotBlank(message = "Assigned to cannot be null")
     private String assignedTo;

}
