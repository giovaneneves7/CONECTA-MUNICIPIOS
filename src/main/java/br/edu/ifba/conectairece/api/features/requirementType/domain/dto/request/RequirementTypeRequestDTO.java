package br.edu.ifba.conectairece.api.features.requirementType.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for receiving requirement type data in API requests.
 * Used when creating or updating requirement type definitions.
 *
 * Contains:
 * - Name of the requirement type.
 * - Description of the requirement type.
 *
 * Author: Caio Alves
 */

public record RequirementTypeRequestDTO (
    @JsonProperty("name")
    String name,

    @JsonProperty("description")
    String description
) {}
