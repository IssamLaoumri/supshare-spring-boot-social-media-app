package com.laoumri.supsharespringbootsocialmediaapp.entities;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Node
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post {
    @Id
    @GeneratedValue
    private UUID post_id;
    private String content;
    private List<String> media;
    private Instant created_at;
    private Profile profile;
}
