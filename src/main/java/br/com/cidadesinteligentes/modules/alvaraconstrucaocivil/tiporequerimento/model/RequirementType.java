package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.model;

import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the type of a {@link Requirement} within the municipal services domain.
 * This entity defines categories or classifications of requirements,
 * helping to distinguish between different kinds of municipal requests.
 *
 * Attributes:
 * - Name: The type name (e.g., "Construction License", "Business Permit").
 * - Description: Additional details about the requirement type.
 *
 * Relationships:
 * - One requirement type can be associated with multiple {@link Requirement} instances.
 *
 * This class is essential for maintaining structured organization and 
 * supporting dynamic addition of new requirement categories in the system.
 * 
 * Author: Caio Alves
 */

@Entity
@Table(name = "requirement_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequirementType extends SimplePersistenceEntity{

    private String name;
    private String description;
}
