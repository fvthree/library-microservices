package com.fvthree.review;

import com.fvthree.app.reviews.DBException;
import com.fvthree.app.reviews.Review;
import com.fvthree.app.reviews.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.fvthree.review.ReviewTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    public void setup() {
        review = Review.builder()
                .id(1L)
                .bookId(1L)
                .reviewDescription("nice")
                .userEmail("felipe@gmail.com")
                .rating(5.0)
                .build();
        reviewRepository.save(review);
    }

    @DisplayName("JUnit for a review should return by find a bookId")
    @Test
    public void givenBookId_whenFindByBookId_thenReviewObject() {

        Long bookId = review.getBookId();

        when(reviewRepository.findByBookId(any(Long.class))).thenReturn(Optional.ofNullable(review));

        Review review = reviewRepository.findByBookId(bookId)
                .orElseThrow(() -> new DBException("review not found."));

        Assertions.assertThat(review).isNotNull();
        Assertions.assertThat(review.getBookId()).isEqualTo(REVIEW_ID);
        Assertions.assertThat(review.getUserEmail()).isEqualTo(REVIEW_USER_EMAIL);
        Assertions.assertThat(review.getReviewDescription()).isEqualTo(REVIEW_DESCRIPTION);
        Assertions.assertThat(review.getRating()).isEqualTo(REVIEW_RATING);
    }

    @DisplayName("JUnit for a review should return by find a userEmail and bookId")
    @Test
    public void givenUserEmailAndBookId_whenFindByReview_thenReviewObject() {

        String userEmail = review.getUserEmail();
        Long bookId = review.getBookId();

        when(reviewRepository.findByUserEmailAndBookId(any(String.class), any(Long.class))).thenReturn(review);

        Review review = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);

        Assertions.assertThat(review).isNotNull();
        Assertions.assertThat(review.getBookId()).isEqualTo(REVIEW_ID);
        Assertions.assertThat(review.getUserEmail()).isEqualTo(REVIEW_USER_EMAIL);
        Assertions.assertThat(review.getReviewDescription()).isEqualTo(REVIEW_DESCRIPTION);
        Assertions.assertThat(review.getRating()).isEqualTo(REVIEW_RATING);
    }
}
