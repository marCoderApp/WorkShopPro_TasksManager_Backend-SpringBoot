package com.equipo.tallerproapp.service.implementations;

import com.equipo.tallerproapp.dto.TaskDTO;
import com.equipo.tallerproapp.enums.TaskStatus;
import com.equipo.tallerproapp.mapper.Mapper;
import com.equipo.tallerproapp.model.Task;
import com.equipo.tallerproapp.model.User;
import com.equipo.tallerproapp.repository.TaskRepository;
import com.equipo.tallerproapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TechService implements com.equipo.tallerproapp.service.interfaces.ITechService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    //TO SEE MY OWN TASKS
    @Override
    public List<TaskDTO> listMyTasks(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalArgumentException("User with email: " +
                "" + email + " not found.")
        );

        User tech = userRepository.findById(user.getId()).orElseThrow(
                ()-> new IllegalArgumentException("Tech with id" + user.getId() +
                        " is not a tech!")
        );

        if(!tech.getRole().name().equals("TECNICO")){
            throw new IllegalArgumentException("Tech with id" + tech.getId() +
                    " is not a tech!");
        }

        List<TaskDTO> taskDtos = taskRepository.findByTechId(tech.getId()).stream()
                .map(Mapper::taskToDTO)
                .toList();


        return taskDtos;
    }

    //CHANGE TASK STATUS
    @Override
    public String changeTaskStatus(Long task_id, String status){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =  userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new EntityNotFoundException("User with email: " + authentication.getName())
        );

        User tech = userRepository.findById(user.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Tech with id" + user.getId() +
                        " is not a tech!")
        );

        if(!tech.getRole().name().equals("TECNICO")){
            throw new IllegalArgumentException("Tech with id" + tech.getId() +
                    " is not a tech!");
        }

         Task task = taskRepository.findById(task_id).orElseThrow(
                 ()-> new EntityNotFoundException("Task with id" + task_id + " not found.")
         );

         try {
             TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
             task.setStatus(taskStatus);
             task.setUpdatedAt(LocalDateTime.now());
             task.setUpdatedBy(String.valueOf(tech.getId()));
             taskRepository.save(task);
         }catch (Exception e){
             throw new IllegalArgumentException("Illegal status: " + status);
         }

        return "Task status has been modified successfully!";
    }

    //TO ADD COMMENTS
    @Override
    public String addComment(Long task_id, String comment){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =  userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new EntityNotFoundException("User with email: " + authentication.getName())
        );

        User tech = userRepository.findById(user.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Tech with id" + user.getId() +
                        " is not a tech!")
        );

        Task task = taskRepository.findById(task_id).orElseThrow(
                ()-> new EntityNotFoundException("Task with id" + task_id + " not found.")
        );

        task.setComment(comment);
        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(String.valueOf(tech.getId()));

        taskRepository.save(task);

        return "Successfully added comment!";
    }

}
