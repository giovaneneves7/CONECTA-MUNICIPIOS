package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RejectionRequestDTO(
    @NotBlank(message = "Technical Responsible registration ID cannot be blank.")
    String registrationId,
    @NotNull(message = "Construction License Requirement ID is mandatory.")
    long constructionLicenseRequirementId,
    @NotBlank(message = "Justification for rejection cannot be blank.")
    String justification
) {}
