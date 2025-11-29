package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.model.RequirementType;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.request.DocumentRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving construction license requirement data in API requests.
 * Used when creating a new construction license request.
 *
 * Contains essential construction-related information such as:
 * - Owner and property details (address, neighborhood, property number, etc.).
 * - Construction information (start and end dates, floors, area, housing units, terrain).
 * - Technical responsible professional.
 * - Attached supporting documents.
 *
 * Validation:
 * - Mandatory fields are annotated with {@link NotNull} and {@link NotBlank}.
 * - Nested validation is applied to {@link TechnicalResponsibleRequestDTO} and {@link DocumentRequestDTO}.
 *
 * Relationships:
 * - Linked to a {@link MunicipalService} through its identifier.
 * - Associated with a {@link RequirementType} through its identifier.
 *
 * Author: Caio Alves
 */
public record ConstructionLicenseRequirementRequestDTO (
    @JsonProperty("solicitanteProfileId")
    @NotNull(message = "Solicitante ID is mandatory.")
    UUID solicitanteProfileId,
    
    @JsonProperty("municipalServiceId")
    @NotNull(message = "Municipal Service ID is mandatory.")
    Long municipalServiceId,

    @JsonProperty("requirementTypeId")
    @NotNull(message = "Requirement Type ID is mandatory.")
    Long requirementTypeId,

    @JsonProperty("owner")
    @NotNull(message = "Owner is mandatory.")
    @NotBlank(message = "Owner cannot be blank.")
    String owner,

    @JsonProperty("phone")
    @NotNull(message = "Phone is mandatory.")
    @NotBlank(message = "Phone cannot be blank.")
    String phone,

    @JsonProperty("cep")
    @NotNull(message = "CEP is mandatory.")
    @NotBlank(message = "CEP cannot be blank.")
    String cep,

    @JsonProperty("cpfCnpj")
    @NotNull(message = "CPF/CNPJ is mandatory.")
    @NotBlank(message = "CPF/CNPJ cannot be blank.")
    String cpfCnpj,

    @JsonProperty("propertyNumber")
    @NotNull(message = "Property number is mandatory.")
    Integer propertyNumber,

    @JsonProperty("neighborhood")
    @NotNull(message = "Neighborhood is mandatory.")
    @NotBlank(message = "Neighborhood cannot be blank.")
    String neighborhood,

    @JsonProperty("constructionAddress")
    @NotNull(message = "Construction address is mandatory.")
    @NotBlank(message = "Construction address cannot be blank.")
    String constructionAddress,

    @JsonProperty("referencePoint")
    String referencePoint,

    @JsonProperty("startDate")
    @NotNull(message = "Start date is mandatory.")
    LocalDateTime startDate,

    @JsonProperty("endDate")
    @NotNull(message = "End date is mandatory.")
    LocalDateTime endDate,

    @JsonProperty("floorCount")
    @NotNull(message = "Floor count is mandatory.")
    Integer floorCount,

    @JsonProperty("constructionArea")
    @NotNull(message = "Construction area is mandatory.")
    Float constructionArea,

    @JsonProperty("housingUnitNumber")
    @NotNull(message = "Housing unit number is mandatory.")
    Integer housingUnitNumber,

    @JsonProperty("terrainArea")
    @NotNull(message = "Terrain area is mandatory.")
    Float terrainArea,

    @JsonProperty("technicalResponsibleRegistrationId") 
    @NotNull(message = "Technical responsible registration ID is mandatory.")
    @NotBlank(message = "Technical responsible registration ID cannot be blank.")
    String technicalResponsibleRegistrationId,

    @JsonProperty("documents")
    @Valid
    List<DocumentRequestDTO> documents
) {}
