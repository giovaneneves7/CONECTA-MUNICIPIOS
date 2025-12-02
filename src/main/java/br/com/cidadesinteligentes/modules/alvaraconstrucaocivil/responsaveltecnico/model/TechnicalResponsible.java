package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model;

import java.io.Serializable;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
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
@DiscriminatorValue("TECHNICAL_RESPONSIBLE")
public class TechnicalResponsible extends Perfil implements Serializable {

    @Column(name = "registration_id", unique = true)
    private String registrationId;

    @Column(name = "responsible_type", nullable = true)
    private String responsibleType;


}
