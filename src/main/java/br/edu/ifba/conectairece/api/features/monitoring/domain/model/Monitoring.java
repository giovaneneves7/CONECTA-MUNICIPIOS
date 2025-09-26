package br.edu.ifba.conectairece.api.features.monitoring.domain.model;


import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Monitoring entity that stores information about Request entity updates made by the public servants.
 *
 * Lifecycle hooks:
 * - {@link #prePersist()} initializes creation and update timestamps when the entity is first saved.
 * - {@link #preUpdate()} updates the modification timestamp whenever the entity is updated.
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "monitorings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Monitoring extends PersistenceEntity {

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @Column(name = "name",  nullable = false, length = 50)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "monitoring_status", nullable = false, length = 20)
    private MonitoringStatus monitoringStatus;


}
