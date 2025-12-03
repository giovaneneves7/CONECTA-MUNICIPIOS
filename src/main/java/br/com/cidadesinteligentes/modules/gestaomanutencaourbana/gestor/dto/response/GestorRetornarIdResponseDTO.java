package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record GestorRetornarIdResponseDTO(
        @JsonProperty("id")
        UUID id
) {}