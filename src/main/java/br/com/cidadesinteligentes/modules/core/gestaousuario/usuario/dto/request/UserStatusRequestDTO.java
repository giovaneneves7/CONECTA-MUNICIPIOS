package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import jakarta.validation.constraints.NotNull;

public record UserStatusRequestDTO(
    @NotNull(message = "Status is mandatory")
    StatusUsuario status
) {
}
