package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.GeneralEvaluationItem;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Monitoring;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model.Update;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.infraestructure.model.StatusHistory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Request entity that stores information about service requests made by citizens.
 * This class contains details such as protocol number, creation and update timestamps,
 * estimated completion date, type, and notes.
 *
 * It is linked to a {@link MunicipalService}, representing the specific service being requested.
 *
 * Lifecycle hooks:
 * - {@link #prePersist()} initializes creation and update timestamps when the entity is first saved.
 * - {@link #preUpdate()} updates the modification timestamp whenever the entity is updated.
 *
 * @author Caio Alves, Giovane Neves
 */

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Request extends PersistenceEntity{

    @Column(nullable = false, unique = true)
    private String protocolNumber;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime estimatedCompletionDate;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private String type;
    private String note;

    @ManyToOne
    @JoinColumn(name = "municipal_service_id")
    private MunicipalService municipalService;

    @OneToMany(mappedBy = "request", orphanRemoval = true)
    private List<Monitoring> monitorings = new ArrayList<>();

    @OneToMany(mappedBy = "request", orphanRemoval = true)
    private List<Update>  updates = new ArrayList<>();

    @OneToMany(mappedBy = "request")
    private List<GeneralEvaluationItem> generalEvaluationItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "status_history_id", unique = true)
    private StatusHistory statusHistory;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now(); 
    }

}
