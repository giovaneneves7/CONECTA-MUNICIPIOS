package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a function in the system.
 * Functions can be assigned to multiple public servants and vice versa.
 *
 * @author Jorge Roberto
 */
@Entity
@Table(name = "functions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Function extends SimplePersistenceEntity implements Serializable {

    @Column(name = "name",  nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "functions")
    private Set<PublicServantProfile> publicServantProfiles = new HashSet<>();
}
