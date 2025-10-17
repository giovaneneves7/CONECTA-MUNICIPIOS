package br.edu.ifba.conectairece.api.features.step.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record StepResponseDTO(
        @JsonProperty("id")
        UUID id
) {
}
