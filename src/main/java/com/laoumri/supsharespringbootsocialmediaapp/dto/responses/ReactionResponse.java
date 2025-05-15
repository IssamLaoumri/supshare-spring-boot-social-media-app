package com.laoumri.supsharespringbootsocialmediaapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReactionResponse {
    private String message;
    private String username;
}
