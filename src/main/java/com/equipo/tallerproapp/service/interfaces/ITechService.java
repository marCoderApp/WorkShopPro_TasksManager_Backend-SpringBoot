package com.equipo.tallerproapp.service.interfaces;

import com.equipo.tallerproapp.dto.TaskDTO;

import java.util.List;

public interface ITechService {

    // TO SEE MY OWN TASKS
    List<TaskDTO> listMyTasks(Long tech_id);

    //CHANGE TASK STATUS
    String changeTaskStatus(Long task_id, String status);

    //TO ADD COMMENTS
    String addComment(Long task_id, String comment);


}
