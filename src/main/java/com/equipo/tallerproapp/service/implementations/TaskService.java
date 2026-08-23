package com.equipo.tallerproapp.service.implementations;

import com.equipo.tallerproapp.dto.AssignTaskDTO;
import com.equipo.tallerproapp.dto.ChangePriorityDTO;
import com.equipo.tallerproapp.dto.TaskDTO;
import com.equipo.tallerproapp.enums.CategoryEnum;
import com.equipo.tallerproapp.enums.PriorityEnum;
import com.equipo.tallerproapp.enums.TaskStatus;
import com.equipo.tallerproapp.mapper.Mapper;
import com.equipo.tallerproapp.model.Task;
import com.equipo.tallerproapp.model.User;
import com.equipo.tallerproapp.repository.TaskRepository;
import com.equipo.tallerproapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TaskService implements com.equipo.tallerproapp.service.interfaces.ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    //CREATE NEW TASK
    @Override
    public TaskDTO createTask(TaskDTO dto, Long tech_id)    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("User not found with email: " + email)
        );

        User tech = userRepository.findById(tech_id).orElseThrow(
                ()-> new RuntimeException("Tech not foun with id" + tech_id)
        );

        if(!tech.getRole().name().equals("TECNICO")){
            throw new IllegalArgumentException("User is not a tech");
        }

        String assignedTo = Long.toString(tech_id);

        Task newTask = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdBy(String.valueOf(user.getId()))
                .createdAt(LocalDateTime.now())
                .priority(dto.getPriority())
                .status(TaskStatus.valueOf(dto.getStatus()))
                .comment(dto.getComment())
                .due_date(dto.getDue_date())
                .user(user)
                .assignedTo(assignedTo)
                .category(CategoryEnum.valueOf(dto.getCategory()))
                .build();

        Task savedTask = taskRepository.save(newTask);

        return Mapper.taskToDTO(savedTask);
    }

    //LIST ALL TASKS
    @Override
    public List<TaskDTO> getAllTasks(){

        List<TaskDTO> tasks = taskRepository.findAll()
                .stream()
                .map(Mapper::taskToDTO)
                .toList();

        return tasks;
    }

    //TO SEE TASK DETAILS
    @Override
    public TaskDTO getTaskById(Long id){

        Task task = taskRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Task not found with id: " + id)
        );

        return Mapper.taskToDTO(task);
    }

    //ASSIGN TASK TO TECH
    @Override
    public String assignTaskToTech(AssignTaskDTO dto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("User not found with email: " + email)
        );

        Task task = taskRepository.findById(dto.getTaskId()).orElseThrow(
                ()-> new RuntimeException("Task not found with id: " + dto.getTaskId())
        );

        User tech = userRepository.findById(dto.getTechId()).orElseThrow(
                ()-> new RuntimeException("Tech not found with id: " + dto.getTechId())
        );

        if(!tech.getRole().name().equals("TECNICO")){
            throw new IllegalArgumentException("User is not a tech");
        }

        task.setAssignedTo(Long.toString(dto.getTechId()));

        Task updatedTask = taskRepository.save(task);

        return "Task assigned to tech: " + "successfully.";

    }

    //LIST TASKS BY TECH
    @Override
    public List<TaskDTO> getTasksByTech(Long id){
        List<TaskDTO> tasks = taskRepository.findByTechId(id).stream()
                .map(Mapper::taskToDTO)
                .toList();

        return tasks;
    }

    //LIST TASKS BY USER
    @Override
    public List<TaskDTO> getTasksByUser(Long id){

        List<TaskDTO> tasks = taskRepository.findByUserId(id).stream()
                .map(Mapper::taskToDTO)
                .toList();
        return tasks;
    }

    //LIST TASKS BY STATUS
    @Override
    public List<TaskDTO> getTasksByStatus(String status){

        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());

        List<TaskDTO> tasks = taskRepository.findByStatus(taskStatus).stream()
                .map(Mapper::taskToDTO)
                .toList();

        return tasks;
    }

    //LIST TASKS BY CREATED DATE
    @Override
    public List<TaskDTO> getTasksByCreatedDate(String date){


        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime startDate = localDate.atStartOfDay();
        LocalDateTime endDate = localDate.atTime(LocalTime.MAX);

        return taskRepository.findByCreatedDate(startDate, endDate)
                .stream()
                .map(Mapper::taskToDTO)
                .toList();
    }

    //LIST TASKS BY DUE DATE
    @Override
    public List<TaskDTO> getTasksByDueDate(String date){
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startDate = localDate.atStartOfDay();
        LocalDateTime endDate = localDate.atTime(LocalTime.MAX);

        return taskRepository.findByDueDate(startDate, endDate)
                .stream()
                .map(Mapper::taskToDTO)
                .toList();
    }

    //CHANGE TASK PRIORITY
    @Override
    public String changeTaskPriority(ChangePriorityDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("User not found with email: " + email)
        );

        Task task = taskRepository.findById(dto.getTask_id()).orElseThrow(
                ()-> new RuntimeException("Task not found with id: " + dto.getTask_id())
        );

        try{
            task.setPriority(PriorityEnum.valueOf(dto.getPriority()
                    .toUpperCase()));
            task.setUpdatedAt(LocalDateTime.now());
            task.setUpdatedBy(String.valueOf(user.getId()));
            taskRepository.save(task);
            return "Priority changed successfully.";
        }catch (Exception e){
            return "Error: " + e.getMessage();
        }
    }
}
