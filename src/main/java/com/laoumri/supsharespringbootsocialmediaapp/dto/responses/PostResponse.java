package com.laoumri.supsharespringbootsocialmediaapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostResponse {
    private UUID post_id;
    private String content;
    private List<String> media;
    private String name;
    private LocalDateTime created_at;
}
