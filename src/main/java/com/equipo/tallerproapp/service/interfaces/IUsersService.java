package com.equipo.tallerproapp.service.interfaces;

import com.equipo.tallerproapp.dto.UsersDTO;

import java.util.List;

public interface IUsersService {

    //LIST ALL THE USERS
    List<UsersDTO> getAllUsers();

    UsersDTO getUserById(Long id);

    UsersDTO editRole(Long id, String role);

    UsersDTO deactivateUser(Long id);

    UsersDTO activateUser(Long id);

    UsersDTO toSeeOwnProfile();

}
