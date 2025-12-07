package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request;

import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.dto.request.PessoaRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO para registar novos usuários
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastrarRequestDTO {

    @JsonProperty("nomeUsuario")
    @NotBlank(message = "Nome de Usuário é obrigatório.")
    @Size(max = 100)
    private String nomeUsuario;

    @JsonProperty(value = "email")
    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email tem que ser válido.")
    @Size(max = 150, message = "Email pode conter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 20)
    private String telefone;

    @JsonProperty(value = "senha")
    @NotBlank(message = "Senha é obrigatório")
    @Size(min = 6, max= 40, message = "A Senha deve conter pelo menos 6 caracteres")
    private String senha;

    @NotNull(message = "Pessoa é obrigatório")
    @Valid
    PessoaRequestDTO pessoa;
}
