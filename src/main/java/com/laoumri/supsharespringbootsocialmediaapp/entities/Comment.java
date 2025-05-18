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
import java.util.List;
import java.util.UUID;

@Node
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Comment {
    @Id
    @GeneratedValue
    private UUID comment_id;
    private String content;
    private Profile author;
    private Instant created_at;
    private Instant updated_at;
    private boolean updated;
    private String media;
    private List<Reaction> reaction;
    @Relationship(type = "HAS_COMMENT", direction = Relationship.Direction.INCOMING)
    private Post post;
}
