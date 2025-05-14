package com.laoumri.supsharespringbootsocialmediaapp.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CommentRequest {
    private String comment;
    private String media;
}
