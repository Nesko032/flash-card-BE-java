package com.fcApp.flashcard.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeckResponse {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;    
}
