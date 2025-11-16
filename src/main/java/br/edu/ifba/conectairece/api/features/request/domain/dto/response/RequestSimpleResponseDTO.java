package br.edu.ifba.conectairece.api.features.request.domain.dto.response;

import java.util.UUID;

/**
 * Simplified response DTO for a Request entity.
 *
 * <p>Contains only the unique identifier, making it suitable for
 * compact responses, references, or nested DTO structures where
 * full request details are not needed.</p>
 *
 * @author Andesson Reis
 */
public record RequestSimpleResponseDTO(UUID id) {
}
