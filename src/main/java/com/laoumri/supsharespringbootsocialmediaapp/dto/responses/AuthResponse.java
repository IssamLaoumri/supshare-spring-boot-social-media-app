package com.laoumri.supsharespringbootsocialmediaapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private UUID id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private int bDay;
    private int bMonth;
    private int bYear;
    private String gender;
    private Set<String> roles;
}
