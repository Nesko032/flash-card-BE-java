package com.fcApp.flashcard.service;

import java.util.List;
import java.util.UUID;

import com.fcApp.flashcard.dto.request.DeckRequest;
import com.fcApp.flashcard.dto.response.DeckResponse;

public interface DeckService {
    DeckResponse createDeck(UUID userId, DeckRequest request);
    DeckResponse getDeckById(UUID deckId);
    List<DeckResponse> getDecksByUserId(UUID userId);
    DeckResponse updateDeck(UUID deckId, UUID userId, DeckRequest request);
    void deleteDeck(UUID deckId, UUID userId);
}
