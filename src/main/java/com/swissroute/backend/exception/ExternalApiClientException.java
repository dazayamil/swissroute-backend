package com.swissroute.backend.exception;

public class ExternalApiClientException extends RuntimeException{
    public ExternalApiClientException(String message){
        super(message);
    }
}
