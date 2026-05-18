package com.fcApp.flashcard.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcApp.flashcard.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, UUID> {
    List<Deck> findByUserId(UUID userID);
    boolean existsByIdAndUserId(UUID id, UUID userId);
}
