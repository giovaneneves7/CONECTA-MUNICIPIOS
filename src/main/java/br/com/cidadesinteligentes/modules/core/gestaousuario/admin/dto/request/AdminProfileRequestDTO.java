package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AdminProfileRequestDTO(
        @JsonProperty(value = "type")
        @NotNull(message = "Type is mandatory.")
        @NotBlank(message = "Type cannot be blank.")
        String type,

        @JsonProperty(value = "imageUrl")
        String imageUrl,

        @JsonProperty("userId")
        @NotNull(message = "UserId is mandatory.")
        UUID userId
) {
}
