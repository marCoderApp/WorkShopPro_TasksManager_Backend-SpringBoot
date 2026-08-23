package com.equipo.tallerproapp.controller;

import com.equipo.tallerproapp.dto.AddCommentDTO;
import com.equipo.tallerproapp.dto.ChangeStatusDTO;
import com.equipo.tallerproapp.service.implementations.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/techs")
public class TechController {

    @Autowired
    private TechService techService;

    //TO SEE MY OWN TASKS
    @GetMapping("/my_tasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> listMyTasks(){

        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(techService.listMyTasks());
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //CHANGE TASK STATUS
    @PatchMapping("/change_status")
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<?> changeTaskStatus(@RequestBody ChangeStatusDTO dto){

        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(techService.changeTaskStatus(dto.getTask_id(), dto.getStatus()));
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }

    //TO ADD COMMENTS
    @PatchMapping("/add_comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addComment(@RequestBody AddCommentDTO dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(techService.addComment(dto.getTask_id(), dto.getComment()));
    }

}

