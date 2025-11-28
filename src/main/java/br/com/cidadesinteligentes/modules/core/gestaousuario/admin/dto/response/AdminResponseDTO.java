package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AdminResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("type")
        String type,

        @JsonProperty("imageUrl")
        String imageUrl
) {
}
