package com.laoumri.supsharespringbootsocialmediaapp.dto.responses;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private Integer statusCode;
    private HttpStatus status;
    private String reason;
    private List<String> message;
    private Code code;
    private Instant timestamp;
}
