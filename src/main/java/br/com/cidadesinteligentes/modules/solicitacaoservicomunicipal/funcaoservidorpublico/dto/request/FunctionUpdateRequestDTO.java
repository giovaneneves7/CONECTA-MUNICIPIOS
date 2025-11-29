package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) for receiving function data in API requests(put and patch).
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
public record FunctionUpdateRequestDTO(
        @JsonProperty(value = "id")
        @NotNull(message = "Id is mandatory.")
        @Positive(message = "Id must be a positive number.")
        Long id,

        @JsonProperty("name")
        @NotNull(message = "Name is mandatory.")
        @NotBlank(message = "Name cannot be blank.")
        String name,

        @JsonProperty("description")
        String description
) {}
