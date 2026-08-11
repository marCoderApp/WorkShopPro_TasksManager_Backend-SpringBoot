package com.equipo.tallerproapp.service;


import com.equipo.tallerproapp.dto.AuthResponse;
import com.equipo.tallerproapp.dto.LoginRequestDTO;
import com.equipo.tallerproapp.dto.RegisterRequestDTO;
import com.equipo.tallerproapp.model.Role;
import com.equipo.tallerproapp.model.User;
import com.equipo.tallerproapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.equipo.tallerproapp.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequestDTO requestDTO){

        if(userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new RuntimeException("El email ya está registrado");
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

    public AuthResponse login(LoginRequestDTO requestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getRole().name());

    }
}
