package com.fcApp.flashcard.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fcApp.flashcard.dto.request.CardRequest;
import com.fcApp.flashcard.dto.response.CardResponse;
import com.fcApp.flashcard.entity.Card;
import com.fcApp.flashcard.entity.Deck;
import com.fcApp.flashcard.exception.ResourceNotFoundException;
import com.fcApp.flashcard.repository.CardRepository;
import com.fcApp.flashcard.repository.DeckRepository;
import com.fcApp.flashcard.service.CardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    /*
    This code will be active when annotations "RequiredArgsConstructor" haven't used.
    public CardServiceImpl(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    } 
    */

    @Override
    public CardResponse createCard(UUID deckId, CardRequest request) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));

        Card card = Card.builder()
                .deck(deck)
                .front(request.getFront())
                .back(request.getBack())
                .cardType(request.getCardType() != null ? request.getCardType() : "basic")
                .isActive(true)
                .build();

        Card saved = cardRepository.save(card);
        
        return mapToResponse(saved);
    }

    @Override 
    public CardResponse getCardById(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        return mapToResponse(card);
    }

    @Override
    public List<CardResponse> getCardsByDeck(UUID deckId) {
        return cardRepository.findByDeckIdAndIsActiveTrue(deckId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override 
    public CardResponse updateCard(UUID cardId, CardRequest request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        
        card.setFront(request.getFront());
        card.setBack(request.getBack());
        if (request.getCardType() != null) {
            card.setCardType(request.getCardType());
        }

        Card updated = cardRepository.save(card);
        return mapToResponse(updated);
    }

    @Override
    public void deleteCard(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        card.setActive(false);
        cardRepository.save(card);
    }

    private CardResponse mapToResponse(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .deckId(card.getDeck().getId())
                .front(card.getFront())
                .back(card.getBack())
                .cardType(card.getCardType())
                .isActive(card.isActive())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }
}
