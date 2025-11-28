package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.response;

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
