package com.laoumri.supsharespringbootsocialmediaapp.nodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.UUID;

@Node
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordHistory {
    @Id @GeneratedValue
    private UUID id;
    private String passwordHash;
    private Instant createdAt;
    @Relationship(type = "HAS_PASSWORD_HISTORY", direction = Relationship.Direction.INCOMING)
    private User belongsTo;
}
