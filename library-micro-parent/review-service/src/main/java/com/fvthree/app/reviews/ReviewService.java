package com.fvthree.app.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review postReview(Map<?,?> request) {
        Review review = Review.builder()
                .bookId(Long.parseLong(request.get("book_id").toString()))
                .userEmail(request.get("user_email").toString())
                .reviewDescription(request.get("review_description").toString())
                .rating(Double.parseDouble(request.get("rating").toString()))
                .createdDate(LocalDateTime.now())
                .build();
        return reviewRepository.save(review);
    }

    public boolean reviewListed(Map<?,?> request) {
        Review review = reviewRepository.findByUserEmailAndBookId(
                request.get("user_email").toString(),
                Long.parseLong(request.get("book_id").toString()));

        return review != null;
    }
}
