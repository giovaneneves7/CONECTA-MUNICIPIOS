package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AssociationActionRequestDTO(

    @NotNull(message = "Technical Responsible ID is mandatory.")
    UUID technicalResponsibleId,

    @NotNull(message = "Construction License Requirement ID is mandatory.")
    long constructionLicenseRequirementId
) {}
