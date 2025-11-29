package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO used to finalize a Construction License Requirement review (Accept or Reject).
 * <p>
 * Contains the identifier of the public servant making the decision and the justification note.
 *
 */
public record ConstructionLicenseRequirementFinalizeRequestDTO(

        @NotNull
        UUID publicServantId,

        @NotBlank(message = "comment is mandatory")
        String comment
) {
}
