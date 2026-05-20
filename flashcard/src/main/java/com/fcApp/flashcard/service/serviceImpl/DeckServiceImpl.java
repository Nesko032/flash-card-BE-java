package com.fcApp.flashcard.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fcApp.flashcard.dto.request.DeckRequest;
import com.fcApp.flashcard.dto.response.DeckResponse;
import com.fcApp.flashcard.entity.Deck;
import com.fcApp.flashcard.entity.User;
import com.fcApp.flashcard.exception.ResourceNotFoundException;
import com.fcApp.flashcard.exception.UnauthorizedException;
import com.fcApp.flashcard.repository.DeckRepository;
import com.fcApp.flashcard.repository.UserRepository;
import com.fcApp.flashcard.service.DeckService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService{
    
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;

    @Override
    public DeckResponse createDeck(UUID userId, DeckRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Deck deck = Deck.builder()
            .user(user)
            .title(request.getTitle())
            .description(request.getDescription())
            .isPublic(request.isPublic())
            .build();

        Deck saved = deckRepository.save(deck);
        
        return mapToResponse(saved);
    }

    @Override
    public DeckResponse getDeckById(UUID deckId) {
        Deck deck = deckRepository.findById(deckId)
            .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));
        
        return mapToResponse(deck);
    }

    @Override
    public List<DeckResponse> getDecksByUserId(UUID userId) {
        return deckRepository.findByUserId(userId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public DeckResponse updateDeck(UUID deckId, UUID userId, DeckRequest request) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));

        if (!deck.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You don't have permission to update this deck");
        }

        deck.setTitle(request.getTitle());
        deck.setDescription(request.getDescription());
        deck.setPublic(request.isPublic());

        Deck updated = deckRepository.save(deck);
        
        return mapToResponse(updated);
    }

    @Override
    public void deleteDeck(UUID deckId, UUID userId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));

        if (!deck.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You don't have permission to delete this deck");
        }

        deckRepository.deleteById(deckId);
    }

    private DeckResponse mapToResponse(Deck deck) {
        return DeckResponse.builder()
                .id(deck.getId())
                .userId(deck.getUser().getId())
                .title(deck.getTitle())
                .description(deck.getDescription())
                .isPublic(deck.isPublic())
                .createdAt(deck.getCreatedAt())
                .build();
    }
}
