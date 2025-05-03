package com.laoumri.supsharespringbootsocialmediaapp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Node
public class RefreshToken {
    @Id
    @GeneratedValue
    private UUID id;
    @Relationship(type = "HAS_REFRESH_TOKEN", direction = Relationship.Direction.INCOMING)
    private User user;
    private String token;
    private Instant expiresAt;
}
