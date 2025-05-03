package com.laoumri.supsharespringbootsocialmediaapp.exceptions.handlers;

import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.ErrorResponse;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.BadRequestException;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.NotFoundException;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(final UnauthorizedException ex){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, List.of(ex.getMessage()), ex.getCode());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND, List.of(ex.getMessage()), ex.getCode());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException ex){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()), ex.getCode());
    }
}
