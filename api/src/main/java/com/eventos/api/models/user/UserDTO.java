package com.eventos.api.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password, String roles) {
}
