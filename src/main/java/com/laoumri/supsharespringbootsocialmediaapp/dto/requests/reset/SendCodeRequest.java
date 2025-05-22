package com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendCodeRequest {
    @Email
    private String email;
}
