package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permission;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a role in the system
 *
 * @author Giovane Neves,Jorge Roberto
 */
@Entity
@Table(name = "roles")
@Data
public class Role extends SimplePersistenceEntity implements Serializable {

    @Column(name = "name",  nullable = false, length = 50)
    String name;
    @Column(name = "description")
    String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "role")
    private List<Profile> profiles = new ArrayList<>();
}
