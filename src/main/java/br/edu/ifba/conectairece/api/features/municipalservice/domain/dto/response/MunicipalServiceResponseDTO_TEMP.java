package br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDTO;

/**
 * Data Transfer Object for representing MunicipalService data in API responses.
 * Contains the municipal service identifier, name, description,
 * and its associated categories.
 *
 * @author Caio Alves, Giovane Neves
 */

public record MunicipalServiceResponseDTO_TEMP(
    @JsonProperty("id")
    Long id,

    @JsonProperty("name")
    String name,

    @JsonProperty("description")
    String description,

    List<CategoryResponseDTO> categories
){}
