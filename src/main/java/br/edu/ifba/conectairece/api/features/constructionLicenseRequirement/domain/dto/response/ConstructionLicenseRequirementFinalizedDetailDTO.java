package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfilePublicDataResponseDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDTO;

/**
 * DTO representing finalized construction license requirement details,
 * including final justification information.
 *
 * @param id                    unique requirement identifier
 * @param createdAt             creation timestamp
 * @param technicalResponsibleStatus current association status
 * @param solicitante           applicant data
 * @param technicalResponsible  responsible technician data
 * @param owner                 property owner name
 * @param phone                 contact phone
 * @param cpfCnpj               CPF/CNPJ identifier
 * @param cep                   postal code
 * @param neighborhood          neighborhood name
 * @param constructionAddress   construction site address
 * @param propertyNumber        property number
 * @param referencePoint        reference point description
 * @param startDate             construction start date
 * @param endDate               construction end date
 * @param floorCount            number of floors
 * @param constructionArea      total construction area
 * @param housingUnitNumber     number of housing units
 * @param terrainArea           land area
 * @param documents             attached documents
 * @param status                requirement current status
 * @param finalJustification    final justification by public servant
 */
public record ConstructionLicenseRequirementFinalizedDetailDTO(

        long id,
        LocalDateTime createdAt,
        AssociationStatus technicalResponsibleStatus,
        ProfilePublicDataResponseDTO solicitante,
        TechnicalResponsibleResponseDTO technicalResponsible,
        String owner,
        String phone,
        String cpfCnpj,
        String cep,
        String neighborhood,
        String constructionAddress,
        Integer propertyNumber,
        String referencePoint,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer floorCount,
        Float constructionArea,
        Integer housingUnitNumber,
        Float terrainArea,
        List<DocumentResponseDTO> documents,

        @JsonProperty("status")
        String status,

        @JsonProperty("finalJustification")
        String finalJustification
) {}
