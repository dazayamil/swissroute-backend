package com.swissroute.backend.exception;

public class FavoriteRouteAccessDeniedException extends RuntimeException {
    public FavoriteRouteAccessDeniedException(String message) {
        super(message);
    }
}
