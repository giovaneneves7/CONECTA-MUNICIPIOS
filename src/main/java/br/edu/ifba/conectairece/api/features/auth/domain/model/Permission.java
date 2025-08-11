package br.edu.ifba.conectairece.api.features.auth.domain.model;

import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity representing a role's permission in the system
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "permissions")
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission  extends PersistenceEntity {

    @Column(name = "name", nullable = false, length = 100)
    String name;

}
