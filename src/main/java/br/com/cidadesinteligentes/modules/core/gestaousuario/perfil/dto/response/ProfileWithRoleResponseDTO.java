package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for sending function data in API responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfileWithRoleResponseDTO(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("role")
        String role,

        @JsonProperty("type")
        String type,

        @JsonProperty("imageUrl")
        String imageUrl
) {
}
