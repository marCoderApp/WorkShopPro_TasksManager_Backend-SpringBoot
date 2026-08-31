package com.equipo.tallerproapp.service.implementations;

import com.equipo.tallerproapp.dto.*;
import com.equipo.tallerproapp.mapper.Mapper;
import com.equipo.tallerproapp.model.Role;
import com.equipo.tallerproapp.model.Task;
import com.equipo.tallerproapp.model.User;
import com.equipo.tallerproapp.repository.TaskRepository;
import com.equipo.tallerproapp.repository.UserRepository;
import com.equipo.tallerproapp.service.interfaces.IUsersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //CREATE NEW USER
    public UsersDTO createUser(RegisterRequestDTO request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        if(request.getRole().name().equals("SUPER_ADMIN")){
            throw new IllegalArgumentException("Super admin cannot be created");
        }


        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

     User newUser = User.builder()
             .name(request.getName())
             .lastname(request.getLastName())
             .dni(request.getDni())
             .email(request.getEmail())
             .password(passwordEncoder.encode(request.getPassword()))
             .role(Role.valueOf(request.getRole().name()))
             .enabled(true)
             .build();

        userRepository.save(newUser);

        return Mapper.userToDTO(newUser);
    }

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User not found with email: " + email)
        );

        List<TaskDTO> tasks = taskRepository.findByUserId(user.getId())
                .stream()
                .map(Mapper::taskToDTO)
                .toList();

        return Mapper.userToOwnProfileDTO(user, tasks);
    }

    @Override
    public OwnProfileDTO editProfile(EditProfileDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User not found with email: " + email)
        );

        if (dto.getDni() != null) {
            user.setDni(dto.getDni());
        }

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getLastname() != null) {
            user.setLastname(dto.getLastname());
        }

        User userUpdated = userRepository.save(user);

        List<TaskDTO> tasks = taskRepository.findByUserId(userUpdated.getId())
                .stream()
                .map(Mapper::taskToDTO)
                .toList();

        return Mapper.userToOwnProfileDTO(userUpdated, tasks);
    }
}
