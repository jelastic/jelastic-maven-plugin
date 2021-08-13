package com.jelastic.exception;

public class JelasticApiException extends RuntimeException {
    public JelasticApiException(final String message) {
        super(message);
    }
}
