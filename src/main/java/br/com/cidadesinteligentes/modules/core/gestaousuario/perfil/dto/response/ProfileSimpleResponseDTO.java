package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import java.util.UUID;

/**
 * Minimal response DTO for a Profile entity.
 *
 * <p>Provides only the unique identifier, used in lightweight
 * response structures or nested DTO compositions where full
 * profile details are not required.</p>
 *
 * @author Andesson Reis
 */
public record ProfileSimpleResponseDTO(UUID id) {
}
