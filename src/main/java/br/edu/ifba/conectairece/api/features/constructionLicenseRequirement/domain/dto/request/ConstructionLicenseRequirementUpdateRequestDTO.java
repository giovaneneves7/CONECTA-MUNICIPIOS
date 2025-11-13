package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for receiving construction license requirement data in API **update** requests.
 * <p>
 * Used when updating an existing construction license request via PUT/PATCH.
 * This DTO mirrors the {@link ConstructionLicenseRequirementRequestDTO} to ensure
 * compatibility with the service layer and validation.
 * <p>
 * Contains essential construction-related information such as:
 * <ul>
 * <li>Owner and property details.</li>
 * <li>Construction information.</li>
 * <li>Technical responsible professional.</li>
 * <li>Attached supporting documents.</li>
 * </ul>
 * <p>
 * Validation:
 * <ul>
 * <li>Mandatory fields are annotated with {@link NotNull} and {@link NotBlank}.</li>
 * <li>Nested validation is applied to {@link DocumentRequestDTO}.</li>
 * </ul>
 *
 * @author Andesson Reis
 */
public record ConstructionLicenseRequirementUpdateRequestDTO(

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
