package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * User login request DTO.
 * Contains email and password sent by the client.
 *
 * @author Jorge Roberto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDTO {
    @JsonProperty(value = "email")
    @NotNull(message = "Email is mandatory.")
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email should be valid.")
    @Size(max = 150, message = "Email must be at most 150 characters.")
    private String email;

    @JsonProperty(value = "password")
    @NotNull(message = "Password is mandatory.")
    @NotBlank(message = "Password cannot be blank.")
    private String password;
}
