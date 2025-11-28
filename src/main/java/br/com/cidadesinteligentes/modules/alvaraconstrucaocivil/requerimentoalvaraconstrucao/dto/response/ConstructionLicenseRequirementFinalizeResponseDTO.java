package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.response;

import java.util.UUID;

/**
 * DTO used to confirm the result of a finalized Construction License Requirement review.
 */
public record ConstructionLicenseRequirementFinalizeResponseDTO(
        Long constructionLicenseRequirementId,
        UUID publicServantId,
        String comment,
        String status
) {
}
