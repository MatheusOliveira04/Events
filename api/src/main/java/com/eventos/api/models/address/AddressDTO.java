package com.eventos.api.models.address;

import java.util.UUID;

public record AddressDTO(UUID id, String city, String uf) {
}
