package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssociationActionRequestDTO(

    @NotBlank(message = "Technical Responsible registration ID cannot be blank.")
    String registrationId,

    @NotNull(message = "Construction License Requirement ID is mandatory.")
    long constructionLicenseRequirementId
) {}
