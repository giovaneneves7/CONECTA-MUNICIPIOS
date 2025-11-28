package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.enums.AssociationStatus;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfilePublicDataResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response.TechnicalResponsibleResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ConstructionLicenseRequirementDetailDTO(

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
    String status
) {}
