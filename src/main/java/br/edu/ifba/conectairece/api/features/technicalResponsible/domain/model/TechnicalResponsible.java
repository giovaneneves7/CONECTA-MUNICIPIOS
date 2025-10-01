package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
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
@DiscriminatorValue("TECHNICAL_RESPONSIBLE")
public class TechnicalResponsible extends Profile{

    @Column(name = "registration_id", nullable = true)
    private String registrationId;

    @Column(name = "responsible_type", nullable = true)
    private String ResponsibleType;


}
