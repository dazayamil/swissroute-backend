package com.swissroute.backend.exception;

public class UserAlreadyExitsException extends RuntimeException {

    public UserAlreadyExitsException() {

        super("USER_ALREADY_EXISTS");
    }
}
