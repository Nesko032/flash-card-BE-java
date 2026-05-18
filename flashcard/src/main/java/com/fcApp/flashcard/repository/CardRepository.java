package com.fcApp.flashcard.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcApp.flashcard.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByDeckId(UUID deckId);
    List<Card> findByDeckIdAndIsActiveTrue(UUID deckId);
}
