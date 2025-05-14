package com.laoumri.supsharespringbootsocialmediaapp.entities;

import com.laoumri.supsharespringbootsocialmediaapp.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Node
public class Profile {
    @Id @GeneratedValue
    private UUID profile;
    private String firstname;
    private String lastname;
    private String username;
    private int bDay;
    private int bMonth;
    private int bYear;
    private EGender gender;
    private String profilePhotoUrl;
    private String profileCoverUrl;
    private List<Post> posts;
}
