package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PermissionRequestAddDTO(

        @JsonProperty("permissionName") @NotBlank
        String permissionName

) {
}
