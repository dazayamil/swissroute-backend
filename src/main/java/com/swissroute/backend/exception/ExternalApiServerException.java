package com.swissroute.backend.exception;

public class ExternalApiServerException extends RuntimeException{
    public ExternalApiServerException(String message){
        super(message);
    }
}
