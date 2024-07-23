package com.eventos.api.models.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDTO(UUID id, String title, String description, String imgUrl, String eventUrl, Boolean remote, LocalDateTime date,
                       UUID addressId, String city, String uf) {
}
