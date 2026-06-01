package com.swissroute.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalApiUnavailableException extends RuntimeException {
    public ExternalApiUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
