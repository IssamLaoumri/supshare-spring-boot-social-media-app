package com.laoumri.supsharespringbootsocialmediaapp.dto.responses;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Code code;
    private Instant timestamp;
    private Object data;
}
