package com.equipo.tallerproapp.controller;

import com.equipo.tallerproapp.dto.TaskDTO;
import com.equipo.tallerproapp.service.implementations.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //CREATE A TASK
    @PostMapping("/create/{tech_id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO dto,
                                              @PathVariable Long tech_id){

        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(taskService.createTask(dto, tech_id));
        }catch(IllegalArgumentException e){
            System.out.println("Task could not be created." +
                    " Reason: " + e.getMessage() + "");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //LIST ALL THE TASKS
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Iterable<TaskDTO>> getAllTasks(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getAllTasks());
    }

    //GET TASK BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }



}
