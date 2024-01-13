package com.fvthree.app.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class APIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    APIException(HttpStatus httpStatus, String message) {
        this.httpStatus=httpStatus;
        this.message=message;
    }
}
