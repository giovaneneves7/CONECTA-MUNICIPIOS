package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnderecoResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("rua")
        String rua,

        @JsonProperty("numero")
        String numero,

        @JsonProperty("bairro")
        String bairro,

        @JsonProperty("cidade")
        String cidade,

        @JsonProperty("estado")
        String estado,

        @JsonProperty("cep")
        String cep,

        @JsonProperty("latitude")
        String latitude,

        @JsonProperty("longitude")
        String longitude
) {}