package com.equipo.tallerproapp.service.interfaces;

import com.equipo.tallerproapp.dto.EditProfileDTO;
import com.equipo.tallerproapp.dto.OwnProfileDTO;
import com.equipo.tallerproapp.dto.UserProfileDTO;
import com.equipo.tallerproapp.dto.UsersDTO;

import java.util.List;

public interface IUsersService {

    //LIST ALL THE USERS
    List<UsersDTO> getAllUsers();

    //GET USER BY ID
    UserProfileDTO getUserById(Long id);

    //EDIT ROLE
    UserProfileDTO editRole(Long id, String role);

    //DEACTIVATE ISER
    UsersDTO deactivateUser(Long id);

    //ACTIVATE USER
    UsersDTO activateUser(Long id);

    //TO SEE OWN PROFILE
    OwnProfileDTO toSeeOwnProfile();

    //EDIT PROFILE DTO
    OwnProfileDTO editProfile(EditProfileDTO dto);
}
