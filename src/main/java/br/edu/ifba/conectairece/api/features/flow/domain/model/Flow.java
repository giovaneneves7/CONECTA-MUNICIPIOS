package br.edu.ifba.conectairece.api.features.flow.domain.model;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a municipal service flow in the system.
 * <p>
 * This class is the main entity for municipal service's flows,
 * containing basic information such as code, name, description and
 * relationship with associated steps.
 * </p>
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "flows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Flow extends PersistenceEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "flow")
    private List<FlowStep> flowSteps = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "municipal_service_id", nullable = false, unique = true)
    private MunicipalService municipalService;

}
