package com.fvthree.app.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByBookId(Long bookId);

    Review findByUserEmailAndBookId(String userEmail, Long bookId);
}
