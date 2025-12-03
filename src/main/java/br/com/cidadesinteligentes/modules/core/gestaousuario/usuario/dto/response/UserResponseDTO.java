package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDTO(
    @JsonProperty("username")
    String username,

    @JsonProperty("email")
    String email,

    @JsonProperty("userStatus")
    StatusUsuario status
) {
}
