package com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.PasswordHistory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.UUID;

public interface PasswordHistoryRepository extends Neo4jRepository<PasswordHistory, UUID> {
    @Query("""
        MATCH (u:User {username: $email})-[:HAS_PASSWORD_HISTORY]->(ph:PasswordHistory)
        RETURN ph
        ORDER BY ph.createdAt DESC
        LIMIT 5
    """)
    List<PasswordHistory> findTop5ByUserEmail(String email);

    @Query("""
        MATCH (u:User {username: $email})-[:HAS_PASSWORD_HISTORY]->(ph:PasswordHistory)
        RETURN ph
        ORDER BY ph.createdAt DESC
        SKIP 5
    """)
    List<PasswordHistory> findOldPasswordHistories(String email);

    @Query("""
        MATCH (ph:PasswordHistory) WHERE ph.id IN $ids
        DETACH DELETE ph
    """)
    void deleteByIds(List<UUID> ids);
}
