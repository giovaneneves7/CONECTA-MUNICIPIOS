package br.edu.ifba.conectairece.api.features.technicalResponsible2.domain.model;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("TECHNICAL_RESPONSIBLE2")
public class TechnicalResponsible2 extends Profile{

    @Column(name = "registration_id, nullable = false")
    private String registrationId;

    @Column(name = "type", nullable = false)
    private String type;

}
