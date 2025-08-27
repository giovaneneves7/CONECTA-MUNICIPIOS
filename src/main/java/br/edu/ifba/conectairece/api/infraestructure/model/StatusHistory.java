package br.edu.ifba.conectairece.api.infraestructure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Entity representing a 'status history' in the system.
 * <p>
 * Uses Lombok and JPA annotations for persistence and auditing.
 *
 * @author Giovane Neves
 */
@Table(name = "status_history")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StatusHistory extends PersistenceEntity{

    @Column(name = "action", nullable = false)
    String action;

    @Column(name = "old_status", nullable = false)
    String oldStatus;

    @Column(name = "new_status", nullable = false)
    String newStatus;

    @Column(name = "changed_by", nullable = false)
    UUID changedBy;

    @Column(name = "changed_at", nullable = false)
    LocalDateTime changedAt;

}
