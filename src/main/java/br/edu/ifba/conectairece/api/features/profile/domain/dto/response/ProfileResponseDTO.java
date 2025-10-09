package br.edu.ifba.conectairece.api.features.profile.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for sending function data in API responses.
 *
 * @author Jorge Roberto, Giovane Neves
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfileResponseDTO (

    @JsonProperty("id")
    UUID id,

    @JsonProperty("type")
    String type,

    @JsonProperty("imageUrl")
    String imageUrl
){}
