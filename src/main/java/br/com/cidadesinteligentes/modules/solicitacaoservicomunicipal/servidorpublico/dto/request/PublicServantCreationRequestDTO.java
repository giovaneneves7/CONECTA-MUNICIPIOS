package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PublicServantCreationRequestDTO(
        @JsonProperty(value = "type")
        @NotNull(message = "Type is mandatory.")
        @NotBlank(message = "Type cannot be blank.")
        String type,

        @JsonProperty(value = "imageUrl")
        String imageUrl,

        @JsonProperty("userId")
        @NotNull(message = "UserId is mandatory.")
        UUID userId,

        @JsonProperty("employeeId")
        String employeeId
) {
}
