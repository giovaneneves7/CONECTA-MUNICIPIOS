package br.edu.ifba.conectairece.api.features.request.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import br.edu.ifba.conectairece.api.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 * @author Caio Alves
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
