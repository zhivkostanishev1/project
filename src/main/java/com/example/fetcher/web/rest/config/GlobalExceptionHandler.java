package com.example.fetcher.web.rest.config;

import com.example.fetcher.core.exception.CommunicationException;
import com.example.fetcher.web.rest.dto.ErrorResponse;
import com.example.fetcher.core.exception.AuthenticationTokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleCustomException(AuthenticationTokenExpiredException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CommunicationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseEntity<ErrorResponse> handleCustomException(CommunicationException ex) {
        ErrorResponse response = new ErrorResponse("Something went wrong with the communication with the Ethereum Client: "
                + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
