package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoriaRetornarIdResponseDTO(
        @JsonProperty("id")
        Long id
) {}