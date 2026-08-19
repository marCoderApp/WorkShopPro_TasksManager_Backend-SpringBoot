package com.equipo.tallerproapp.service.interfaces;

import com.equipo.tallerproapp.dto.AssignTaskDTO;
import com.equipo.tallerproapp.dto.TaskDTO;

import java.util.List;

public interface ITaskService {

    //CREATE A NEW TASK
    TaskDTO createTask(TaskDTO dto,
                       Long tech_id);

    //LIST ALL TASKS
     List<TaskDTO> getAllTasks();

     //TO SEE TASK DETAILS
    TaskDTO getTaskById(Long id);

    //ASSIGN TASK TO TECH
    String assignTaskToTech(AssignTaskDTO dto);

    //LIST TASKS BY TECH
    List<TaskDTO> getTasksByTech(Long id);

    //LIST TASKS BY USER
    List<TaskDTO> getTasksByUser(Long id);

    //LIST TASKS BY STATUS
    List<TaskDTO> getTasksByStatus(String status);

    //LIST TASKS BY CREATED DATE
    List<TaskDTO> getTasksByCreatedDate(String date);

    //LIST TASKS BY DUE DATE
    List<TaskDTO> getTasksByDueDate(String date);


}
