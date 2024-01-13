package com.fvthree.app.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<?,?> request) {
        try {
            return createSuccessResponse(AuthConstants.LOGIN_SUCCESS_MSG, Map.of("data", userService.login(request)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<?,?> request) {
        try {
            return createSuccessResponse(AuthConstants.REGISTER_SUCCESS_MSG, Map.of("data", userService.register(request)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }

    private ResponseEntity<HttpResponse> createSuccessResponse(String message, Map<?,?> data) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                    .message(message)
                    .status(HttpStatus.OK.name())
                    .statusCode(HttpStatus.OK.value())
                    .data(data)
                    .build());
    }

    private ResponseEntity<HttpResponse> buildErrorResponse(Exception e) {
        String message = e.getMessage();
        if (e instanceof DataAccessException) {
            if (e.getMessage().contains("duplicate key value violates unique"))
                message = "Record already exists";
        }
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .message(message)
                        .build()
        );
    }

}
