package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response;

/**
 * Minimal response structure for a Construction License Requirement entity.
 *
 * <p>Contains only the unique identifier, intended for lightweight
 * response payloads or nested projections where full details are unnecessary.</p>
 *
 * @author Andesson Reis
 */
public record ConstructionLicenseRequirementSimpleResponseDTO(Long id) {
}
