package br.edu.ifba.conectairece.api.features.user.domain.dto.request;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UserStatusRequestDTO(
    @NotNull(message = "Status is mandatory")
    UserStatus status
) {
}
