package br.edu.ifba.conectairece.api.features.requirement.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;
import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract base entity for different types of requirements requested in municipal services.
 * Provides common attributes and relationships shared across all requirement types.
 *
 * Attributes:
 * - Creation timestamp.
 * - Associated {@link MunicipalService}.
 * - Associated {@link RequirementType}.
 *
 * Relationships:
 * - One requirement can have multiple {@link Document} entries.
 * - Requirements are linked to specific municipal services and requirement types.
 * - Concrete implementations (e.g., {@link ConstructionLicenseRequirement}) extend this class.
 *
 * Uses JOINED inheritance strategy to allow different requirement types 
 * to share common fields while storing type-specific fields in separate tables.
 * 
 * Author: Caio Alves
 */

@Entity
@Table(name = "requirements")
@Inheritance(strategy = InheritanceType.JOINED) 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Requirement extends SimplePersistenceEntity{

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @ManyToOne
    @JoinColumn(name = "municipal_service_id")
    private MunicipalService municipalService;

    @ManyToOne(optional = false)
    @JoinColumn(name = "requirement_type_id", nullable = false)
    private RequirementType requirementType;

    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();
}
