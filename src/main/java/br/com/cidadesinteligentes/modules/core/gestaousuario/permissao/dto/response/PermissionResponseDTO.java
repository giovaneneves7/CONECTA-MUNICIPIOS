package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PermissionResponseDTO(
        @JsonProperty("name")
        String name
) {
}
