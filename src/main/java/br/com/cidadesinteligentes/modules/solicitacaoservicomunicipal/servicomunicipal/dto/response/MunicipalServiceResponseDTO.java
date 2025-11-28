package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing MunicipalService data in API responses.
 * Contains the municipal service identifier, name, description,
 * and its associated categories.
 *
 * @author Caio Alves, Giovane Neves
 */

public record MunicipalServiceResponseDTO(
    @JsonProperty("id")
    Long id,

    @JsonProperty("name")
    String name,

    @JsonProperty("description")
    String description

){}
