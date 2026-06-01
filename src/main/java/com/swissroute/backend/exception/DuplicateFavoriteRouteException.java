package com.swissroute.backend.exception;

public class DuplicateFavoriteRouteException extends RuntimeException {

    public DuplicateFavoriteRouteException(String message) {
        super(message);
    }
}
