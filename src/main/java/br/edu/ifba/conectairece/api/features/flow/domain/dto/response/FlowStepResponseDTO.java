package br.edu.ifba.conectairece.api.features.flow.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record FlowStepResponseDTO(
    @JsonProperty("id")
    UUID id
){}
