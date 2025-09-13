package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing technical responsible data in API responses.
 * Provides details about the professional assigned to a requirement.
 *
 * Contains:
 * - Identifier.
 * - Name.
 * - Email.
 * - Phone number.
 *
 * @author Caio Alves, Giovane Neves
 */

public record TechnicalResponsibleResponseDTO (

    @JsonProperty("id")
    UUID id,

    @JsonProperty("name")
    String name,

    @JsonProperty("email")
    String email,

    @JsonProperty("phone")
    String phone
){}
