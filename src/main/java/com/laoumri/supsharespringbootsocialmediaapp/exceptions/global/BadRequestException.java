package com.laoumri.supsharespringbootsocialmediaapp.exceptions.global;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final Code code;
    public BadRequestException(Code code, String message) {
        super(message);
        this.code = code;
    }
}
