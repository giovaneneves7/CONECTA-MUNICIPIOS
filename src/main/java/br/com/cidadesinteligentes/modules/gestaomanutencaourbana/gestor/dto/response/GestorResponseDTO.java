package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GestorResponseDTO(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("imagemUrl")
        String imagemUrl,

        @JsonProperty("tipo")
        String tipo
) {
    // Construtor compacto ou lógica para garantir que o tipo seja sempre retornado caso venha nulo do mapper
    public GestorResponseDTO {
        if (tipo == null) {
            tipo = "GESTOR_MANUTENCAO";
        }
    }
}