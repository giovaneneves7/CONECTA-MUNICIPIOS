package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioResponseDTO(
    @JsonProperty("id")
    UUID id,

    @JsonProperty("nomeUsuario")
    String nomeUsuario,

    @JsonProperty("email")
    String email,

    @JsonProperty("status")
    StatusUsuario status
) {
}
