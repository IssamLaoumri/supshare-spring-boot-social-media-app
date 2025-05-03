package com.laoumri.supsharespringbootsocialmediaapp.entities;

import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Node
public class Role implements GrantedAuthority {
    @Id @GeneratedValue
    private UUID id;
    private ERole roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
