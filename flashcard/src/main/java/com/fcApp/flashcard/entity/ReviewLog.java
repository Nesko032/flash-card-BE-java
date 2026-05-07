package com.fcApp.flashcard.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int rating;

    @Column(name = "interval_days")
    private int intervalDays = 1;

    @Column(name = "ease_factor")
    private float easeFactor = 2.5f;

    @Column(name = "reviewed_at", updatable = false)
    private LocalDateTime reviewedAt;

    @Column(name = "next_review_at")
    private LocalDateTime nextReviewAt;

    @PrePersist
    protected void onCreate() { reviewedAt = LocalDateTime.now(); }
}