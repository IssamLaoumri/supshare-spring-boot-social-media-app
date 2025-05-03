package com.laoumri.supsharespringbootsocialmediaapp.repositories;

import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends Neo4jRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
