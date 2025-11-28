package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for an administrator to assign a Technical Responsible profile to a user.
 * This DTO carries the necessary data for the assignment operation, including the user's ID and registration details.
 *
 * @author Caio Alves
 */
public record AdminAssingnTechnicalResponsibleDTO(
    
    @NotNull(message = "O ID do usuário é obrigatório.")
    UUID userId,

    @NotBlank(message = "O número de registro é obrigatório.")
    String registrationId,

    @NotBlank(message = "O tipo de responsável é obrigatório.")
    String responsibleType,

    String imageUrl
) {

}
