package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AdminProfileListResponseDTO(
    @JsonProperty("id")
    UUID id,

    @JsonProperty("type")
    String type,

    @JsonProperty("imageUrl")
    String imageUrl,

    @JsonProperty("specificType")
    String specificType
) {

}
