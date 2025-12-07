package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import jakarta.validation.constraints.NotNull;

public record UsuarioStatusRequestDTO(
    @NotNull(message = "Status é obrigatório")
    StatusUsuario status
) {
}
