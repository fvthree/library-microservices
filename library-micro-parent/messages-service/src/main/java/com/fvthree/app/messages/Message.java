package com.fvthree.app.messages;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String question;

    @Column(nullable = true)
    private String adminEmail;

    @Column(nullable = true)
    private String response;

    @Column(nullable = false)
    private boolean closed;
}
