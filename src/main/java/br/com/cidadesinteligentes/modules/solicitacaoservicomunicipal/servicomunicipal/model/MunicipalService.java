package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model;

import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Flow;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a MunicipalService entity that models a public service provided by the municipality.
 * This class stores service-specific information such as name and description.
 *
 * - One municipal service can have multiple requests.
 * - A municipal service can belong to multiple categories.
 *
 * @author Caio Alves
 */

@Entity
@Table(name = "municipal_services")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "service_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class MunicipalService extends SimplePersistenceEntity {

    private String name;
    private String description;

    @OneToMany(mappedBy = "municipalService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests = new ArrayList<>();

    @OneToOne(mappedBy = "municipalService")
    private Flow flow;

}
