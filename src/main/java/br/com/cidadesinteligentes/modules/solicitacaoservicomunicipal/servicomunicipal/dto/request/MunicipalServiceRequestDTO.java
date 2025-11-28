package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving MunicipalService data in API requests.
 * Used when creating or updating municipal service information.
 * Includes the service's name, description, and associated category IDs.
 *
 * @author Caio Alves
 */
public record MunicipalServiceRequestDTO (
    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotBlank(message = "Name is cannot be blank.")
    String name,

    @JsonProperty("description")
    String description,

    @NotEmpty(message = "At least one category must be informed.")
    List<Integer> categoryIds
) {}
