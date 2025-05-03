package com.laoumri.supsharespringbootsocialmediaapp.security.utils;

import com.laoumri.supsharespringbootsocialmediaapp.entities.Role;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.RoleRepository;
import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ERole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (ERole erole : ERole.values()) {
            roleRepository.findByRoleName(erole).orElseGet(() -> {
                log.info("Inserting missing role: {}", erole);
                return roleRepository.save(Role.builder().roleName(erole).build());
            });
        }
    }
}
