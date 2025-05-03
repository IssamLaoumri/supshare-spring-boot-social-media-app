package com.laoumri.supsharespringbootsocialmediaapp.exceptions.global;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final Code code;
    public NotFoundException(Code code, String message) {
        super(message);
        this.code = code;
    }
}
