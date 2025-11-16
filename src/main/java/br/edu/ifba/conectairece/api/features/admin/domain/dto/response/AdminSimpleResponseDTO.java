package br.edu.ifba.conectairece.api.features.admin.domain.dto.response;

import java.util.UUID;

/**
 * Simple response representation for an Admin entity.
 *
 * <p>This DTO provides minimal identification data used in lightweight
 * response payloads across the application.</p>
 *
 * @author Andesson Reis
 */
public record AdminSimpleResponseDTO(
        UUID id
) {
}
