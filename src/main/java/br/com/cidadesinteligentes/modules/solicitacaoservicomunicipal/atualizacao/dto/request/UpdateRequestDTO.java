package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record UpdateRequestDTO(

        @NotNull(message = "requestId is required")
        @JsonProperty("requestId")
        UUID requestId,

        @NotNull(message = "timestamp is required")
        @JsonProperty("timestamp")
        LocalDateTime timestamp,

        @JsonProperty("note")
        String note
) {
}
