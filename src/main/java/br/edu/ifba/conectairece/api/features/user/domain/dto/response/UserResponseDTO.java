package br.edu.ifba.conectairece.api.features.user.domain.dto.response;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDTO(
    @JsonProperty("username")
    String username,

    @JsonProperty("email")
    String email,

    @JsonProperty("userStatus")
    UserStatus status
) {
}
