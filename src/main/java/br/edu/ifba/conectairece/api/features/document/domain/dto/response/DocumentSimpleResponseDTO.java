package br.edu.ifba.conectairece.api.features.document.domain.dto.response;

import java.util.UUID;

/**
 * Simplified representation of a Document entity.
 *
 * <p>Contains only the unique identifier, suitable for compact
 * response structures or nested DTO compositions.</p>
 *
 * @author Andesson Reis
 */
public record DocumentSimpleResponseDTO(UUID id) {
}
