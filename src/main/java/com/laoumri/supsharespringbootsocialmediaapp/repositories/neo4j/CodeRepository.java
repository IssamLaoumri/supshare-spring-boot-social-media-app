package com.laoumri.supsharespringbootsocialmediaapp.repositories;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.Code;
import jakarta.annotation.Nonnull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CodeRepository extends Neo4jRepository<Code, UUID> {
    @Query("""
        MATCH (u:User)-[:HAS_RESET_CODE]->(c:Code)
        WHERE c.code = $code AND u.id = $userId
        RETURN c
    """)
    Optional<Code> findByCodeAndUserId(String code, UUID userId);

    void deleteById(@Nonnull UUID id);
}
