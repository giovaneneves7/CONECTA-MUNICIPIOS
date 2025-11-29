package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Giovane Neves 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponseDTO{

	@JsonProperty("id")
    private UUID id;

	@JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("userStatus")
    private UserStatus userStatus;
}