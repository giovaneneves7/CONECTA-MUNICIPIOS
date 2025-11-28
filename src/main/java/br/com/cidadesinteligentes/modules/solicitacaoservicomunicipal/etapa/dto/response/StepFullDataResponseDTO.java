package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record StepFullDataResponseDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("code")
        String code,
        @JsonProperty("imageUrl")
        String imageUrl,
        @JsonProperty("order")
        long order
) {
}
