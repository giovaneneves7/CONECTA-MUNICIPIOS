package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model;

import java.time.LocalDateTime;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a construction license requirement requested within the municipal services.
 * This class extends {@link Requirement}, inheriting its base properties and relationships.
 *
 * It contains specific information for construction-related requests, such as:
 * - Owner and property identification.
 * - Construction details (address, number of floors, area, housing units).
 * - Timeline for construction start and end.
 * - Association with a {@link TechnicalResponsible}.
 *
 * Relationships:
 * - One construction license requirement has one technical responsible.
 * - It also inherits relationships with {@link MunicipalService}, {@link RequirementType}, and {@link Document}.
 *
 * @author Caio Alves
 */

@Entity
@Table(name = "construction_license_requirements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionLicenseRequirement extends Requirement {

    private String owner;
    private String phone;
    private String cep;
    private String cpfCnpj;
    private Integer propertyNumber;
    private String neighborhood;
    private String constructionAddress;
    private String referencePoint;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer floorCount;
    private Float constructionArea;
    private Integer housingUnitNumber;
    private Float terrainArea;

    @ManyToOne(optional = false)
    @JoinColumn(name = "technical_responsible_id", nullable = false)
    private TechnicalResponsible technicalResponsible;

    @Enumerated(EnumType.STRING)
    @Column(name = "technical_responsible_status", nullable = false)
    private AssociationStatus technicalResponsibleStatus;

    @Column(name = "rejection_justification")
    private String rejectionJustification;
}
