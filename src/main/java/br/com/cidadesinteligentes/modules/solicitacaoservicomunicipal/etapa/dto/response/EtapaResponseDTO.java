package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record EtapaResponseDTO(
        @JsonProperty("id")
        UUID id
) {
}
