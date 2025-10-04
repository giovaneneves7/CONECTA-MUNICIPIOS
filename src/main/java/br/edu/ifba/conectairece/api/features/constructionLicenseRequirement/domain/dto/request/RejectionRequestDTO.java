package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RejectionRequestDTO(
    @NotBlank(message = "Justification for rejection cannot be blank.")
    String justification
) {}
