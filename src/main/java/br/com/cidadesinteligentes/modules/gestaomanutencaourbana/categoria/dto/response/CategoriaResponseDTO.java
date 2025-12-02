package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// Record para resposta
@JsonInclude(JsonInclude.Include.NON_NULL) // Não retorna campos nulos no JSON final
public record CategoriaResponseDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("nome")
        String nome,

        @JsonProperty("descricao")
        String descricao
) {}