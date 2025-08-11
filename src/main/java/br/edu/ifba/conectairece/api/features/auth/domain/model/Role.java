package br.edu.ifba.conectairece.api.features.auth.domain.model;

import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

/**
 * Entity representing a role in the system
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "roles")
@Data
public class Role extends SimplePersistenceEntity {

    @Column(name = "name",  nullable = false, length = 50)
    String name;
    @Column(name = "description")
    String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

}
