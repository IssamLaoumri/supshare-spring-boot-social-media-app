package com.laoumri.supsharespringbootsocialmediaapp.security.utils;

import com.laoumri.supsharespringbootsocialmediaapp.documents.ChatMessage;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Role;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.mongo.ChatRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j.RoleRepository;
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
    private final ChatRepository chatRepository;

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
