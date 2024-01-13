package com.fvthree.app.reviews;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> postReview(@RequestBody Map<?,?> request) {
        try {
            return createSuccessResponse("Success creating a review", Map.of("data", reviewService.postReview(request)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> reviewListed(@RequestBody Map<?,?> request) {
        try {
            return createSuccessResponse("Is review listed", Map.of("reviewListed", reviewService.reviewListed(request)));
        } catch(Exception e) {
            return buildErrorResponse(e);
        }
    }

    private ResponseEntity<ReviewHttpResponse> createSuccessResponse(String message, Map<?, ?> data) {
        return ResponseEntity.ok(ReviewHttpResponse.builder()
                .message(message)
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build());
    }

    private ResponseEntity<ReviewHttpResponse> buildErrorResponse(Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof DataAccessException) {
            if (ex.getMessage().contains("duplicate key value violates unique"))
                message = "Record already exists";
        }

        return ResponseEntity.ok(ReviewHttpResponse.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
}
