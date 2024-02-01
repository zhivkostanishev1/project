package com.example.fetcher.core.exception;

public class AuthenticationTokenExpiredException extends RuntimeException {

    public AuthenticationTokenExpiredException(String message) {
        super(message);
    }

}
