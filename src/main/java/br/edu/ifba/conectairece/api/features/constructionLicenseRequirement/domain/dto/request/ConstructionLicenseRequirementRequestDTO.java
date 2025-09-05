package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRequestDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConstructionLicenseRequirementRequestDTO {

    @JsonProperty("municipalServiceId")
    @NotNull(message = "Municipal Service ID is mandatory.")
    private Integer municipalServiceId;

    @JsonProperty("requirementTypeId")
    @NotNull(message = "Requirement Type ID is mandatory.")
    private Integer requirementTypeId;

    @JsonProperty("owner")
    @NotNull(message = "Owner is mandatory.")
    @NotBlank(message = "Owner cannot be blank.")
    private String owner;

    @JsonProperty("phone")
    @NotNull(message = "Phone is mandatory.")
    @NotBlank(message = "Phone cannot be blank.")
    private String phone;

    @JsonProperty("cep")
    @NotNull(message = "CEP is mandatory.")
    @NotBlank(message = "CEP cannot be blank.")
    private String cep;

    @JsonProperty("cpfCnpj")
    @NotNull(message = "CPF/CNPJ is mandatory.")
    @NotBlank(message = "CPF/CNPJ cannot be blank.")
    private String cpfCnpj;

    @JsonProperty("propertyNumber")
    @NotNull(message = "Property number is mandatory.")
    private Integer propertyNumber;

    @JsonProperty("neighborhood")
    @NotNull(message = "Neighborhood is mandatory.")
    @NotBlank(message = "Neighborhood cannot be blank.")
    private String neighborhood;

    @JsonProperty("constructionAddress")
    @NotNull(message = "Construction address is mandatory.")
    @NotBlank(message = "Construction address cannot be blank.")
    private String constructionAddress;

    @JsonProperty("referencePoint")
    private String referencePoint;

    @JsonProperty("startDate")
    @NotNull(message = "Start date is mandatory.")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    @NotNull(message = "End date is mandatory.")
    private LocalDateTime endDate;

    @JsonProperty("floorCount")
    @NotNull(message = "Floor count is mandatory.")
    private Integer floorCount;

    @JsonProperty("constructionArea")
    @NotNull(message = "Construction area is mandatory.")
    private Float constructionArea;

    @JsonProperty("housingUnitNumber")
    @NotNull(message = "Housing unit number is mandatory.")
    private Integer housingUnitNumber;

    @JsonProperty("terrainArea")
    @NotNull(message = "Terrain area is mandatory.")
    private Float terrainArea;

    @JsonProperty("technicalResponsible")
    @Valid
    @NotNull(message = "Technical responsible is mandatory.")
    private TechnicalResponsibleRequestDTO technicalResponsible;

    @JsonProperty("documents")
    @Valid
    private List<DocumentRequestDTO> documents;
}
