package com.fvthree.app.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> postMessage(@RequestBody Map<?,?> request) {
        try {
            return createSuccessHttpResponse(MessageConstants.SUCCESS_CREATE_MESSAGE, Map.of("data", messageService.postMessage(request)));
        } catch(Exception e) {
            return createErrorHttpResponse(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateMessage(@RequestBody Map<?,?> request) {
        try {
            return createSuccessHttpResponse(MessageConstants.SUCCESS_CLOSE_A_MESSAGE, Map.of("data", messageService.closeMessage(request)));
        } catch(Exception e) {
            return createErrorHttpResponse(e);
        }
    }

    private ResponseEntity<MessageHttpResponse> createSuccessHttpResponse(String message, Map<?,?> data) {
        return ResponseEntity.ok(
                MessageHttpResponse.builder()
                        .message(message)
                        .code(200)
                        .status(HttpStatus.OK.name())
                        .data(data)
                        .build()
        );
    }

    private ResponseEntity<MessageHttpResponse> createErrorHttpResponse(Exception e) {
        return ResponseEntity.ok(
                MessageHttpResponse.builder()
                        .message(e.getMessage())
                        .code(500)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .build()
        );
    }
}
