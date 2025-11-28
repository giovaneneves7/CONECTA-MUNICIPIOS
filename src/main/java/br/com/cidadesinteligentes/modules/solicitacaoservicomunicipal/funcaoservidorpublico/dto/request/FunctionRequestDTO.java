package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for receiving function data in API requests.
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
public record FunctionRequestDTO (
        @JsonProperty("name")
        @NotNull(message = "Name is mandatory.")
        @NotBlank(message = "Name cannot be blank.")
        String name,

        @JsonProperty("description")
        String description
) {}
