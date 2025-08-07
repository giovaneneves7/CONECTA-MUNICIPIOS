package br.edu.ifba.conectairece.api.features.auth.domain.dto.request;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * DTO to register new users.
 * Contains name, email, password and role.
 *
 * @author Jorge Roberto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must have at least 6 characters.")
    private String password;

    @NotNull
    private Role role;
}
