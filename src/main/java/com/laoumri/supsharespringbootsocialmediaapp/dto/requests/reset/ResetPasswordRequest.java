package com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordRequest {
    private String password;
    private String confirmPassword;
}
