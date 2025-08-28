package br.edu.ifba.conectairece.api.features.function.domain.model;

import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;
import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a function in the system.
 * Functions can be assigned to multiple public servants and vice versa.
 *
 * @author Jorge Roberto
 */
@Entity
@Table(name = "functions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Function extends SimplePersistenceEntity implements Serializable {

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "functions")
    private Set<PublicServantProfile> publicServantProfiles = new HashSet<>();
}
