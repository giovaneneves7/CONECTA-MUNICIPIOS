package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model;


import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.MonitoringStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
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
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "monitoring_status", nullable = false, length = 20)
    private MonitoringStatus monitoringStatus;

    @ManyToOne
    @JoinColumn(name = "etapa_id", nullable = false)
    private Etapa etapa;


    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
