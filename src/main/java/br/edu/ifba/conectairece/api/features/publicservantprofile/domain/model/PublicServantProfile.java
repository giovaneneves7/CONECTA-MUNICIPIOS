package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model;

import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employees_functions",
            joinColumns = @JoinColumn(name = "public_servant_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private Set<Function> functions;

}
