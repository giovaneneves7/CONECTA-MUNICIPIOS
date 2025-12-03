package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * User login response DTO.
 *
 * Returned to the client after successful authentication.
 *
 * @author Jorge Roberto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("token")
    private String token;

    @JsonProperty("userStatus")
    private StatusUsuario userStatus;
}
