package com.laoumri.supsharespringbootsocialmediaapp.nodes;

import com.laoumri.supsharespringbootsocialmediaapp.enums.ECode;
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
public class Code {
    @Id @GeneratedValue
    private UUID id;
    private String code;
    private Instant validUntil;
    private ECode type;
    @Relationship(type = "HAS_RESET_CODE", direction = Relationship.Direction.INCOMING)
    private User user;
}
