package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model;

import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * Represents a public servant profile in the system.
 * This entity extends the base Profile class and adds specific attributes for public servants.
 *
 * @author Jorge Roberto
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("PUBLIC_SERVANT")
public class PublicServantProfile extends Profile implements Serializable {

    @Column(name = "employee_id")
    private String employeeId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employees_functions",
            joinColumns = @JoinColumn(name = "public_servant_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private Set<Function> functions;

}
