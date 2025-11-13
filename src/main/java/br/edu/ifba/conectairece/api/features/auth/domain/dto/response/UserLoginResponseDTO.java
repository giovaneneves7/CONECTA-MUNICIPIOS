package br.edu.ifba.conectairece.api.features.auth.domain.dto.response;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;

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
    private UserStatus userStatus;
}
