package com.laoumri.supsharespringbootsocialmediaapp.repositories;

import com.laoumri.supsharespringbootsocialmediaapp.entities.RefreshToken;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    @Query("MATCH (u:User)-[rel:HAS_REFRESH_TOKEN]->(r:RefreshToken) WHERE u.id = $userId DELETE rel, r")
    void deleteByUser(UUID userId);
}
