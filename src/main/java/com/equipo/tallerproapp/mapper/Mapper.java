package com.equipo.tallerproapp.mapper;

import com.equipo.tallerproapp.dto.OwnProfileDTO;
import com.equipo.tallerproapp.dto.TaskDTO;
import com.equipo.tallerproapp.dto.UserProfileDTO;
import com.equipo.tallerproapp.dto.UsersDTO;
import com.equipo.tallerproapp.model.User;

import java.util.List;

public class Mapper {

    //USER TO DTO
    public static UsersDTO userToDTO(User user){

        if(user == null) return null;

        return UsersDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .dni(user.getDni())
                .email(user.getEmail())
                .role(user.getRole().name())
                .isEnabled(user.isEnabled())
                .build();
    }

    //USER PROFILE TO DTO
    public static UserProfileDTO userProfileToDTO(User user, List<TaskDTO> tasks){
        if(user == null) return null;

        if(tasks == null) return null;

        UserProfileDTO dto = UserProfileDTO.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .dni(user.getDni())
                .email(user.getEmail())
                .role(user.getRole().name())
                .isEnabled(user.isEnabled())
                .tasks(tasks)
                .build();

        return dto;
    }

    //TASK TO DTO
    public static TaskDTO taskToDTO(com.equipo.tallerproapp.model.Task task){
        if(task == null) return null;

        TaskDTO dto = TaskDTO.builder()
                .id(task.getTask_id())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .createdBy(task.getCreatedBy())
                .updatedBy(task.getUpdatedBy())
                .priority(task.getPriority())
                .status(task.getStatus().name())
                .comment(task.getComment())
                .assignedTo(task.getAssignedTo())
                .due_date(task.getDue_date())
                .category(task.getCategory().name())
                .build();

        return dto;
    }

    //USER TO OWNPROFILEDTO
    public static OwnProfileDTO userToOwnProfileDTO(User user,
                                                     List<TaskDTO> tasks){
        if(user == null) return null;
        if(tasks == null) return null;

        OwnProfileDTO dto = OwnProfileDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .dni(user.getDni())
                .email(user.getEmail())
                .role(user.getRole().name())
                .isEnabled(user.isEnabled())
                .tasks(tasks)
                .build();


        return dto;
    }


}
