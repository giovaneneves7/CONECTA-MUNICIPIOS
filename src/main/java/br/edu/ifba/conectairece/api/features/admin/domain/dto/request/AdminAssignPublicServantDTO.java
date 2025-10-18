package br.edu.ifba.conectairece.api.features.admin.domain.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for an administrator to assign a Public Servant profile to a user.
 * This DTO contains the required information to create and link a new Public Servant profile.
 *
 * @author Caio Alves
 */
public record AdminAssignPublicServantDTO(
    
    @NotNull(message = "O ID do usuário é obrigatório.")
    UUID userId,

    @NotBlank(message = "A matrícula do funcionário é obrigatória.")
    String employeeId,

    String imageUrl
) {

}
