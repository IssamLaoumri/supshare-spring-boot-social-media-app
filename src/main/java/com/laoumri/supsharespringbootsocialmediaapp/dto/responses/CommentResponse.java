package com.laoumri.supsharespringbootsocialmediaapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private UUID comment_id;
    private String comment;
    private String media;
    private String author;
    private Instant created_at;
    private Instant updated_at;
    private boolean updated;
    private long likes_count;
    private long dislikes_count;
}
