package com.fvthree.app.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/book")
public class BookController {

    private Logger logger = LogManager.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getBooks() {
        return "books";
    }

    @PostMapping
    public ResponseEntity<BookHttpResponse> create(@RequestBody Map<?, ?> request) {
        try {
            return createSuccessResponse(BookServiceConstants.CREATE_BOOK_MSG, Map.of(BookServiceConstants.BOOK_KEY, bookService.createBook(request)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookHttpResponse> delete(@PathVariable("id") Long id) {
        try {
            return createSuccessResponse(BookServiceConstants.REMOVE_BOOK_MSG, Map.of(BookServiceConstants.BOOK_KEY, bookService.removeBook(id)));
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    @PutMapping("/{id}/increase")
    public ResponseEntity<BookHttpResponse> increaseBookQuantity(@PathVariable("id") Long id) {
        try {
            return createSuccessResponse(BookServiceConstants.INCREASE_BOOK_MSG, Map.of(BookServiceConstants.BOOK_KEY, bookService.increaseBookQuantity(id)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }

    @PutMapping("/{id}/decrease")
    public ResponseEntity<BookHttpResponse> decreaseBookQuantity(@PathVariable("id") Long id) {
        try {
            return createSuccessResponse(BookServiceConstants.DECREASE_BOOK_MSG, Map.of(BookServiceConstants.BOOK_KEY, bookService.decreaseBookQuantity(id)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }

    private ResponseEntity<BookHttpResponse> createSuccessResponse(String message, Map<?, ?> data) {
        return ResponseEntity.ok(BookHttpResponse.builder()
                .message(message)
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build());
    }

    private ResponseEntity<BookHttpResponse> buildErrorResponse(Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof DataAccessException) {
            if (ex.getMessage().contains("duplicate key value violates unique"))
                message = "Record already exists";
        }

        return ResponseEntity.ok(BookHttpResponse.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }


}
