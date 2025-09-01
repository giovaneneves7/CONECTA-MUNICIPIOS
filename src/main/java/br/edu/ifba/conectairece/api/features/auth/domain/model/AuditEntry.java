package br.edu.ifba.conectairece.api.features.auth.domain.model;

import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an 'audit entry' in the system, for audit.
 * <p>
 * Uses Lombok and JPA annotations for persistence and auditing.
 *
 * @author Giovane Neves
 */
@Table(name = "audit_entry")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuditEntry extends PersistenceEntity {

    @Column(name = "entity_name", nullable = false)
    String entityName;

    @Column(name = "entity_id", nullable = false)
    String entityId;

    @Column(name = "action", nullable = false)
    String action;

    String oldValue; // TODO: mudar para json
    String newValue; // TODO: mudar para json

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    UUID createdBy;

    @Column(name = "correlation_id", nullable = false)
    UUID correlationId;

    @Column(name = "integrity_hash", nullable = false)
    String integrityHash;

}
