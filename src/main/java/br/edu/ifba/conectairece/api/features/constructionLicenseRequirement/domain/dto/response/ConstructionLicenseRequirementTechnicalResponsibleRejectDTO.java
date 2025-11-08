package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author Giovane Neves
 */
public record ConstructionLicenseRequirementTechnicalResponsibleRejectDTO(

    @JsonProperty("id")
    Long id,

    @JsonProperty("rejectionJustification")
    String rejectionJustification
){}
