package com.laoumri.supsharespringbootsocialmediaapp.dto.requests;

import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReactionRequest {
    private EReaction reaction;
    private String message;
}
