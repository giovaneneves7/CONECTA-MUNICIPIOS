package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO de resposta para login corretoo
 *
 * Retorna alguns dados depois da autenticação correta do cliente, como o token
 *
 * @author Jorge Roberto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioLoginResponseDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("token")
    private String token;

    @JsonProperty("statusUsuario")
    private StatusUsuario statusUsuario;
}
