package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model;

import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a technical responsible professional assigned to a requirement.
 * This entity stores contact information of the professional overseeing 
 * technical aspects of construction or other municipal service requirements.
 *
 * Attributes:
 * - Name.
 * - Email.
 * - Phone.
 *
 * Relationships:
 * - A technical responsible is linked to a {@link ConstructionLicenseRequirement}.
 *
 * This class ensures proper traceability of professionals accountable for municipal requests.
 * 
 * Author: Caio Alves
 */

@Entity
@Table(name = "technical_responsibles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalResponsible extends PersistenceEntity {

    private String name;
    private String email;
    private String phone;

}
