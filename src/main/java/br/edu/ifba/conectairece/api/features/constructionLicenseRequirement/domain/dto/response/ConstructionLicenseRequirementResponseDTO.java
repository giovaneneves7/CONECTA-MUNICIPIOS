package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;


/**
 * Data Transfer Object for representing construction license requirement data in API responses.
 * Provides detailed information about an existing construction license request.
 *
 * Contains:
 * - Requirement identifier and creation timestamp.
 * - Owner and property details.
 * - Construction information (address, area).
 * - Technical responsible professional details.
 * - Associated requirement type.
 * - Attached supporting documents.
 *
 * This DTO is used to return complete construction license information
 * when queried by clients or listed in responses.
 *
 * Author: Caio Alves, Giovane Neves
 */

public record ConstructionLicenseRequirementResponseDTO(

    @JsonProperty("id")
    Long id,

    @JsonProperty("createdAt")
    LocalDateTime createdAt,

    @JsonProperty("owner")
    String owner,

    @JsonProperty("phone")
    String phone,

    @JsonProperty("cep")
    String cep,

    @JsonProperty("cpfCnpj")
    String cpfCnpj,

    @JsonProperty("constructionAddress")
    String constructionAddress,

    @JsonProperty("constructionArea")
    Float constructionArea,

    @JsonProperty("technicalResponsibleName") 
    String technicalResponsibleName,

    //@JsonProperty("requirementType")
    //RequirementTypeResponseDTO requirementType,

   // @JsonProperty("documents")
    //List<DocumentResponseDTO> documents

    AssociationStatus technicalResponsibleStatus,

    @JsonProperty("status")
    String status
){}
