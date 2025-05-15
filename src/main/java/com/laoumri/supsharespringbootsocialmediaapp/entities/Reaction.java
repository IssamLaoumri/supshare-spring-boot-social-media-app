package com.laoumri.supsharespringbootsocialmediaapp.entities;

import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.UUID;

@Node
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reaction {
    @Id
    @GeneratedValue
    private UUID reactionId;
    private EReaction reactionType;
    @Relationship(type = "MAPPED_BY", direction = Relationship.Direction.OUTGOING)
    private Post post;
    @Relationship(type = "MAPPED_BY", direction = Relationship.Direction.OUTGOING)
    private Comment comment;
    @Relationship(type = "MAPPED_BY", direction = Relationship.Direction.OUTGOING)
    private Profile profile;
}
