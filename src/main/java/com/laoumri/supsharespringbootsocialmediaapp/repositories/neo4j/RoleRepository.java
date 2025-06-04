package com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.Role;
import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ERole;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends Neo4jRepository<Role, UUID> {
    Optional<Role> findByRoleName(ERole roleName);
}
