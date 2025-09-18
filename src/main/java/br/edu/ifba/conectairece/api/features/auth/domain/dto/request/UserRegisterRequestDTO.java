package br.edu.ifba.conectairece.api.features.auth.domain.dto.request;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.person.domain.dto.request.PersonRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * DTO to register new users.
 *
 * @author Jorge Roberto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDTO {
    @NotNull(message = "Username is mandatory.")
    @NotBlank(message = "Username cannot be blank.")
    @Size(max = 100)
    private String username;

    @Email
    @Size(max = 150)
    private String email;

    @NotNull(message = "phone is mandatory")
    @NotBlank(message = "phone cannot be blank")
    @Size(max = 20)
    private String phone;

    @NotNull(message = "Password is mandatory.")
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max= 40, message = "Password must have at least 6 characters.")
    private String password;

    @NotNull(message = "Person is mandatory.")
    @Valid
    PersonRequestDTO person;
}
