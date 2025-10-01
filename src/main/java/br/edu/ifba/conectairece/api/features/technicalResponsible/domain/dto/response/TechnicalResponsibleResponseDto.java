package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing TechnicalResponsible data in API responses.
 * Contains the identifier, registration ID and type.
 *
 * @author Caio Alves
 */

public record TechnicalResponsibleResponseDto(
    @JsonProperty("id")
    UUID id,

    @JsonProperty("registrationId")
    String registrationId,

    @JsonProperty("responsibleType")
    String ResponsibleType
) {

}
