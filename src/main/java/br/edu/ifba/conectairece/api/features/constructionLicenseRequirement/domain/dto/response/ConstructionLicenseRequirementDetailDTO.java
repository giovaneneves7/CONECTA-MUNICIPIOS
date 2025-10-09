package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfilePublicDataResponseDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;

public record ConstructionLicenseRequirementDetailDTO(

    long id,
    LocalDateTime createdAt,
    AssociationStatus technicalResponsibleStatus,
    ProfilePublicDataResponseDTO solicitante,
    TechnicalResponsibleResponseDto technicalResponsible,
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
    List<DocumentResponseDTO> documents
) {}
