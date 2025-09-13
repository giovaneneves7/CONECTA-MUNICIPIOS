package br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving MunicipalService data in API requests.
 * Used when creating or updating municipal service information.
 * Includes the service's name, description, and associated category IDs.
 *
 * @author Caio Alves
 */
public record MunicipalServiceRequestDto (
    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotNull(message = "Name is cannot be blank.")
    String name,

    @JsonProperty("description")
    String description,

    @NotEmpty(message = "At least one category must be informed.")
    List<Integer> categoryIds
) {}
