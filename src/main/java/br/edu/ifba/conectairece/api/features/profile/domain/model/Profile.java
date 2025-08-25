package br.edu.ifba.conectairece.api.features.profile.domain.model;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a user profile in the system.
 * <p>
 * This class is the main entity for managing user profiles,
 * containing basic information such as profile type, image URL, and
 * relationship with associated users.
 * </p>
 *
 * @author Jorge Roberto
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
    private String imageURL;

    @OneToMany(mappedBy = "profile")
    private List<User> users = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
