package com.laoumri.supsharespringbootsocialmediaapp.repositories;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.Profile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, UUID> {
    boolean existsByUsername(String username);
}
