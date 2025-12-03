package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnderecoRetornarIdResponseDTO(
        @JsonProperty("id")
        Long id
) {}