package com.laoumri.supsharespringbootsocialmediaapp.exceptions.global;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import lombok.Getter;

@Getter
public class InvalidOperationException extends RuntimeException {
  private final Code code;

  public InvalidOperationException(Code code, String message) {
    super(message);
    this.code = code;
    }
}
