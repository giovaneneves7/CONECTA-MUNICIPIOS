package br.edu.ifba.conectairece.api.features.citizen.domain.model;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


/**
 * Represents a Citizen entity that extends the Profile base class.
 * This class is used to store citizen-specific information including government profile data.
 *
 * @author Jorge Roberto
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CITIZEN")
public class Citizen extends Profile {

    @Column(name = "gov_profile_snapshot", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String govProfileSnapshot;
}
