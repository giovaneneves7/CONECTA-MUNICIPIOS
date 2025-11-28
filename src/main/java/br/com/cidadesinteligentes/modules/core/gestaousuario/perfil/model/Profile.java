package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Role;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.User;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing a user profile in the system.
 * <p>
 * This class is the main entity for managing user profiles,
 * containing basic information such as profile type, image URL, and
 * relationship with associated users.
 * </p>
 *
 * @author Jorge Roberto, Giovane Neves
 */
@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "profile_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class Profile extends PersistenceEntity {

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
