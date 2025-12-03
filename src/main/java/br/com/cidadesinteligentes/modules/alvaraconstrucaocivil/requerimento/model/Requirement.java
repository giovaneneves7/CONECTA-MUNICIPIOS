package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimento.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.comentariorequerimento.model.Comment;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimento.enums.RequirementStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.model.Document;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.model.RequirementType;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
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
 * Author: Caio Alves, Andesson Reis
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Requirement extends ServicoMunicipal{

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "requirement_type_id", nullable = false)
    private RequirementType requirementType;

    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitante_user_id", nullable = false)
    private Usuario solicitante;

    /**
     * The single Comment or justification associated with this requirement.
     * This is the inverse side of the One-to-One relationship.
     */
    @OneToOne(mappedBy = "requirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private Comment comment;

    @Column(name = "status")
    private RequirementStatus status;
}
