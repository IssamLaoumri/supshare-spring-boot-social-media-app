package com.laoumri.supsharespringbootsocialmediaapp.nodes;

import com.laoumri.supsharespringbootsocialmediaapp.enums.ERequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.time.Instant;
import java.util.UUID;

@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FriendRequest {
    @Id @GeneratedValue
    private UUID id;

    @TargetNode
    private User receiver;

    @Property
    private ERequestStatus status;

    private Instant createdAt;
}
