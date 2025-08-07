package br.edu.ifba.conectairece.api.features.auth.domain.dto.response;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * User login response DTO.
 * Contains the JWT token, email, role and user status.
 *
 * Returned to the client after successful authentication.
 *
 * @author Jorge Roberto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("userStatus")
    private UserStatus userStatus;
}
