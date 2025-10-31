package br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.model;

import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.enums.GeneralEvaluationItemStatus;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "general_valuation_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class GeneralEvaluationItem extends SimplePersistenceEntity {

    @Column(name = "note")
    private String note;

    @Column(name = "status",  nullable = false)
    private GeneralEvaluationItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Column(name = "name")
    private String name;
}
