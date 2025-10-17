package br.edu.ifba.conectairece.api.features.step.domain.model;

import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a municipal service flow step in the system.
 * <p>
 * This class is the main entity for municipal service's flow steps,
 * containing basic information such as name, code, order and
 * relationship with associated flow.
 * </p>
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "steps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Step extends PersistenceEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code",  nullable = false, unique = true)
    private String code;

    @Column(name = "image_url",  nullable = false)
    private String imageUrl;


    @OneToMany(mappedBy = "step")
    private List<FlowStep> flowSteps = new ArrayList<>();

    @OneToMany(mappedBy = "step")
    private List<Monitoring> monitorings = new ArrayList<>();

}
