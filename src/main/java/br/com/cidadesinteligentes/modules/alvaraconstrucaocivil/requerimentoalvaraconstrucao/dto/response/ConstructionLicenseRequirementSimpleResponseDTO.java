package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.response;

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
