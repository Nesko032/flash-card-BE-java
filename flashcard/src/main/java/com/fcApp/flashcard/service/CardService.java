package com.fcApp.flashcard.service;

import java.util.List;
import java.util.UUID;

import com.fcApp.flashcard.dto.request.CardRequest;
import com.fcApp.flashcard.dto.response.CardResponse;

public interface CardService {
    CardResponse createCard(UUID deckId, CardRequest request);
    CardResponse getCardById(UUID cardId);
    List<CardResponse> getCardsByDeck(UUID deckId);
    CardResponse updateCard(UUID cardId, CardRequest request);
    void deleteCard(UUID cardId);
}
