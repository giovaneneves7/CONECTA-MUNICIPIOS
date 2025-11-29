package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Giovane Neves
 *
 */
public record StepRequestDTO(
        @JsonProperty("name")
        @NotNull(message = "'name' is required")
        @NotBlank(message = "'name' cannot be blank")
        String name,

        @JsonProperty("code")
        @NotNull(message = "'code' is required")
        @NotBlank(message = "'code' cannot be blank")
        String code,

        @JsonProperty("imageUrl")
        String imageUrl
) {
}
