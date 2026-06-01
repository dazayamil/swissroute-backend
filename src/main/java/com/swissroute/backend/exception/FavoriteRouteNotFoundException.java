package com.swissroute.backend.exception;

public class FavoriteRouteNotFoundException extends RuntimeException {
    public FavoriteRouteNotFoundException(String message) {
        super(message);
    }
}
