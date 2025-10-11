package br.edu.ifba.conectairece.api.features.permission.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PermissionResponseDTO(
        @JsonProperty("name")
        String name
) {
}
