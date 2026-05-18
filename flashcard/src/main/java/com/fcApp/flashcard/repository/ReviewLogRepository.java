package com.fcApp.flashcard.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcApp.flashcard.entity.ReviewLog;

@Repository
public interface ReviewLogRepository extends JpaRepository<ReviewLog, UUID> {
    List<ReviewLog> findByUserIdAndNextReviewAtBefore(UUID userId, LocalDateTime dateTime);
}
