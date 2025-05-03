package com.laoumri.supsharespringbootsocialmediaapp.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int bDay;
    private int bMonth;
    private int bYear;
    private String gender;
    private Set<String> roles;
}
