package com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "INVALID_EMAIL_ADDRESS")
    private String email;
    @Length(min = 6, max = 50, message = "INVALID_PASSWORD")
    private String password;
}
