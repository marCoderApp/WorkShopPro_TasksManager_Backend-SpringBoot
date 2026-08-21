package com.equipo.tallerproapp.controller;

import com.equipo.tallerproapp.dto.AssignTaskDTO;
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

    //ASSIGN A TASK TO TECH
    @PatchMapping("/assign_to")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<String> assignTaskToTech(@RequestBody AssignTaskDTO dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.assignTaskToTech(dto));
    }

  //GET TASK BY USER ID
    @GetMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Iterable<TaskDTO>> getTasksByUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksByUser(id));
    }

    //GET TASK BY TECH ID
    @GetMapping("/tech/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Iterable<TaskDTO>> getTasksByTech(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksByTech(id));
    }

    //GET TASKS BY STATUS
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Iterable<TaskDTO>> getTasksByStatus(@PathVariable String status){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksByStatus(status));
    }

    //GET TASKS BY CREATED DATE
    @GetMapping("/created_date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Iterable<TaskDTO>> getTasksByCreatedDate(@PathVariable String date){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksByCreatedDate(date));
    }

    //GET TASKS BY DUE DATE
    @GetMapping("/due_date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Iterable<TaskDTO>> getTasksByDueDate(@PathVariable String date){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksByDueDate(date));
    }
}
