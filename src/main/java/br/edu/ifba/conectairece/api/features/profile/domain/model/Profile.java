package br.edu.ifba.conectairece.api.features.profile.domain.model;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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
public class Profile extends SimplePersistenceEntity implements Serializable {

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
