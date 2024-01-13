package com.fvthree.app.reviews;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = true)
    private double rating;

    @Column(nullable = true)
    private String reviewDescription;
}
