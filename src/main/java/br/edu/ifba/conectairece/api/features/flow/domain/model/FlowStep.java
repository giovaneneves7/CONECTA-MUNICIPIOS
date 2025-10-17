package br.edu.ifba.conectairece.api.features.flow.domain.model;

import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flow_steps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class FlowStep extends PersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", nullable = false)
    private Flow flow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private Step step;

    @Column(name = "step_order")
    private long stepOrder;
}