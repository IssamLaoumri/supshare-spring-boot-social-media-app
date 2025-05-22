package com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyCodeRequest {
    @Length(min = 5, max = 5)
    private String code;
}
