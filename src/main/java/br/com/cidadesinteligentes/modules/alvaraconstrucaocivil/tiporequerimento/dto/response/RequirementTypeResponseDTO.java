package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing requirement type data in API responses.
 * Provides details about a requirement type.
 *
 * Contains:
 * - Identifier.
 * - Name.
 * - Description.
 *
 * @author Caio Alves, Giovane Neves
 */

public record RequirementTypeResponseDTO (

    @JsonProperty("id")
    Long id,

    @JsonProperty("name")
    String name,

    @JsonProperty("description")
    String description
){}
