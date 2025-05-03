package com.laoumri.supsharespringbootsocialmediaapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Node
public class User implements UserDetails {
    @Id @GeneratedValue
    private UUID id;
    private String username;
    @JsonIgnore
    private String password;

    @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.OUTGOING)
    private Set<Role> roles;
    @Relationship(type = "has_profile", direction = Relationship.Direction.OUTGOING)
    private Profile profile;

    private boolean accountNonExpired;      // Indicates whether the user's account has expired
    private boolean accountNonLocked;       // Indicates whether the user is locked or unlocked
    private boolean credentialsNonExpired;  // Indicates whether the user's password or credentials have expired
    private boolean enabled;                // Indicates whether the user is enabled or not.


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
