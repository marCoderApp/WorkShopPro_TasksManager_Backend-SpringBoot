package com.equipo.tallerproapp.service.interfaces;

import com.equipo.tallerproapp.dto.OwnProfileDTO;
import com.equipo.tallerproapp.dto.UserProfileDTO;
import com.equipo.tallerproapp.dto.UsersDTO;

import java.util.List;

public interface IUsersService {

    //LIST ALL THE USERS
    List<UsersDTO> getAllUsers();

    UserProfileDTO getUserById(Long id);

    UserProfileDTO editRole(Long id, String role);

    UsersDTO deactivateUser(Long id);

    UsersDTO activateUser(Long id);

    OwnProfileDTO toSeeOwnProfile();

}
