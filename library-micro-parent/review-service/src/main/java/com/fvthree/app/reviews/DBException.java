package com.fvthree.app.reviews;

public class DBException extends RuntimeException {

    private String message;

    public DBException(String message) {
        super(message);
    }
}
