package com.equipo.tallerproapp.service;


import com.equipo.tallerproapp.dto.AuthResponse;
import com.equipo.tallerproapp.dto.LoginRequestDTO;
import com.equipo.tallerproapp.dto.RegisterRequestDTO;
import com.equipo.tallerproapp.model.Role;
import com.equipo.tallerproapp.model.User;
import com.equipo.tallerproapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.equipo.tallerproapp.service.JwtService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequestDTO requestDTO){

        if(userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new IllegalStateException("The email is already in use.");
        }

        User user = User.builder()
                .name(requestDTO.getName())
                .lastname(requestDTO.getLastName())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .dni(requestDTO.getDni())
                .role(Role.valueOf(requestDTO.getRole().name()))
                .email(requestDTO.getEmail())
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getRole().name());

    }

    //login
    public AuthResponse login(LoginRequestDTO requestDTO){

        User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("Credentials are not valid.")
        );

        if(user.isBlocked()){
            if (LocalDateTime.now().isBefore(user.getBlockDate())){
                throw new LockedException("Blocked account!, try again after: " +
                        user.getBlockDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }else{
                resetAttemps(user);
            }
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getEmail(),
                            requestDTO.getPassword()
                    )
            );
        }catch (BadCredentialsException e){
            registerFailedAttemps(user);
            throw new BadCredentialsException("Credentials are not valid.");
        }

        resetAttemps(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getRole().name());
    }

    private void registerFailedAttemps(User user){
        int failedAttemps = user.getFailedAttemps() + 1;
        user.setFailedAttemps(failedAttemps);

        if(failedAttemps >= 5){
            user.setBlocked(true);
            user.setBlockDate(LocalDateTime.now().plusMinutes(15));
        }

        userRepository.save(user);
    }

    private void resetAttemps(User user){
        user.setFailedAttemps(0);
        user.setBlocked(false);
        user.setBlockDate(null);
        userRepository.save(user);
    }
}
