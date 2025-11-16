package br.edu.ifba.conectairece.api.features.requirementType.domain.dto.response;

/**
 * Lightweight DTO representing a Requirement Type entity.
 *
 * <p>Provides only the identifier, typically used in minimal or
 * nested response structures where full details are unnecessary.</p>
 *
 * @author Andesson Reis
 */
public record RequirementTypeSimpleResponseDTO(Long id) {
}
