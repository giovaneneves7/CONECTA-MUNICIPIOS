package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ManutencaoUrbanaRetornarIdResponseDTO(
        @JsonProperty("id")
        Long id
) {}