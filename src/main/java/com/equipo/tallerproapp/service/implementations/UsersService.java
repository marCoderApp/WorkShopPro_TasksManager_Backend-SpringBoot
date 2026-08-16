package com.equipo.tallerproapp.service.implementations;

import com.equipo.tallerproapp.dto.OwnProfileDTO;
import com.equipo.tallerproapp.dto.TaskDTO;
import com.equipo.tallerproapp.dto.UserProfileDTO;
import com.equipo.tallerproapp.dto.UsersDTO;
import com.equipo.tallerproapp.mapper.Mapper;
import com.equipo.tallerproapp.model.Role;
import com.equipo.tallerproapp.model.Task;
import com.equipo.tallerproapp.model.User;
import com.equipo.tallerproapp.repository.TaskRepository;
import com.equipo.tallerproapp.repository.UserRepository;
import com.equipo.tallerproapp.service.interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    //LIST ALL USERS
    @Override
    public List<UsersDTO> getAllUsers(){
        return userRepository.findAll().
                stream()
                .map(Mapper::userToDTO)
                .toList();

    }

    //GET USER BY ID
    @Override
    public UserProfileDTO getUserById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        List<TaskDTO> tasks = taskRepository.findByUserId(id)
                .stream()
                .map(Mapper::taskToDTO)
                .toList();

        return Mapper.userProfileToDTO(user, tasks);

    }

    //EDIT ROLE
    @Override
    public UserProfileDTO editRole(Long id, String role){

        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found with id: " + id)
        );

        user.setRole(Role.valueOf(role));

        List<TaskDTO> tasks = taskRepository.findByUserId(id)
                .stream()
                .map(Mapper::taskToDTO)
                .toList();

        UserProfileDTO dto = Mapper.userProfileToDTO(userRepository.save(user),
                tasks);
        return dto;
    }

    //DEACTIVATE USER
    @Override
    public UsersDTO deactivateUser(Long id){

        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found with id: " + id)
        );

        user.setEnabled(false);

        UsersDTO dto = Mapper.userToDTO(userRepository.save(user));

        return dto;
    }

    //ACTIVATE USER
    @Override
    public UsersDTO activateUser(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found with id: " + id)
        );

        user.setEnabled(true);

        return Mapper.userToDTO(userRepository.save(user));
    }

    //TO SEE OWN PROFILE
    @Override
    public OwnProfileDTO toSeeOwnProfile() {
        return null;
    }



}
