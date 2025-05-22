package com.laoumri.supsharespringbootsocialmediaapp.dto.responses.reset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindUserResponse {
    private String email;
    private String profilePhotoUrl;
}
