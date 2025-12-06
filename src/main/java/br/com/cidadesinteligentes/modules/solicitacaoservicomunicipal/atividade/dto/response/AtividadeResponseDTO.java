package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AtividadeResponseDTO(
        @JsonProperty("id")
        UUID id
) {
}
