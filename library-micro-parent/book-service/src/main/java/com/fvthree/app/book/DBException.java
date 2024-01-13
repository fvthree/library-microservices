package com.fvthree.app.book;

public class DBException extends RuntimeException {

    private String message;

    DBException(String message) {
        super(message);
    }
}
