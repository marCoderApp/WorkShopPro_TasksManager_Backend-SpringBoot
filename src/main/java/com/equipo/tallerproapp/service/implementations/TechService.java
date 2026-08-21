package com.equipo.tallerproapp.service.implementations;

import com.equipo.tallerproapp.dto.TaskDTO;
import com.equipo.tallerproapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechService implements com.equipo.tallerproapp.service.interfaces.ITechService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsersService usersService;

    //TO SEE MY OWN TASKS
    public List<TaskDTO> listMyTasks(Long tech_id){
        return null;
    }

    //CHANGE TASK STATUS
    public String changeTaskStatus(Long task_id, String status){
        return null;
    }

    //TO ADD COMMENTS
    public String addComment(Long task_id, String comment){
        return null;
    }

}
