package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de login
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginRequestDTO {
    @JsonProperty(value = "email")
    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email tem que ser válido.")
    @Size(max = 150, message = "Email pode conter no máximo 150 caracteres")
    private String email;

    @JsonProperty(value = "senha")
    @NotBlank(message = "Senha é obrigatório")
    private String senha;
}
