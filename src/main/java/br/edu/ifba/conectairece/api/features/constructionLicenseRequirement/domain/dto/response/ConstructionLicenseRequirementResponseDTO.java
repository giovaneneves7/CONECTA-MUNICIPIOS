package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.response.RequirementTypeResponseDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
 * Author: Caio Alves
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConstructionLicenseRequirementResponseDTO {

     @JsonProperty("id")
    private Integer id;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("cpfCnpj")
    private String cpfCnpj;

    @JsonProperty("constructionAddress")
    private String constructionAddress;

    @JsonProperty("constructionArea")
    private Float constructionArea;

    @JsonProperty("technicalResponsible")
    private TechnicalResponsibleResponseDTO technicalResponsible;

    @JsonProperty("requirementType")
    private RequirementTypeResponseDTO requirementType;

    @JsonProperty("documents")
    private List<DocumentResponseDTO> documents;
    
}
