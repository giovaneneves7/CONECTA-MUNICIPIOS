package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * DTO com o cargo do perfil
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerfilComCargoResponseDTO(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("cargo")
        String cargo,

        @JsonProperty("tipo")
        String tipo,

        @JsonProperty("imagemUrl")
        String imagemUrl
) {
}
