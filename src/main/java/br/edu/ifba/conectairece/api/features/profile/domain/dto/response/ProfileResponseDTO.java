package br.edu.ifba.conectairece.api.features.profile.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for sending function data in API responses.
 *
 * @author Jorge Roberto, Giovane Neves
 */
public record ProfileResponseDTO (

    @JsonProperty("id")
    UUID id,

    @JsonProperty("type")
    String type,

    @JsonProperty("imageUrl")
    String imageUrl
){}
