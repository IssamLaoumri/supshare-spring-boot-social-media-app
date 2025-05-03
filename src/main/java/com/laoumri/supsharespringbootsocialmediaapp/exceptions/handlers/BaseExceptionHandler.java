package com.laoumri.supsharespringbootsocialmediaapp.exceptions.handlers;


import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.ErrorResponse;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

public abstract class BaseExceptionHandler {
    ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, List<String> message, Code errorCode) {
        ErrorResponse res = ErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(message)
                .reason(status.getReasonPhrase())
                .timestamp(Instant.now())
                .code(errorCode)
                .build();
        return ResponseEntity.status(status).body(res);
    }
}
