package com.equipo.tallerproapp.seed;

import com.equipo.tallerproapp.model.*;
import com.equipo.tallerproapp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("superadmin@taller.com").isEmpty()) {
            createSuperAdmin();
            log.info(">>> SUPER_ADMIN creado: superadmin@taller.com / Admin123!");
        }else{
            log.info("Super Admin already exists");
        }
    }

    private void createSuperAdmin() {
        User superAdmin = User.builder()
                .name("Super Admin")
                .email("superadmin@taller.com")
                .password(passwordEncoder.encode("Admin123!"))
                .role(Role.SUPER_ADMIN)
                .enabled(true)
                .build();

        userRepository.save(superAdmin);
    }
}