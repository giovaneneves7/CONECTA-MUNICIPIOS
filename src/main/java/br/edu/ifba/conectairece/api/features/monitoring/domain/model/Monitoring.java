package br.edu.ifba.conectairece.api.features.monitoring.domain.model;


import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Monitoring entity that stores information about Request entity updates made by the public servants.
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

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name",  nullable = false, length = 50)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "monitoring_status", nullable = false, length = 20)
    private MonitoringStatus monitoringStatus;


}
