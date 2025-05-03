package com.laoumri.supsharespringbootsocialmediaapp.exceptions.global;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.Code;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
  private final Code code;
  public UnauthorizedException(String message, Code code) {
      super(message);
      this.code = code;
  }

}
