package br.edu.ifba.conectairece.api.features.auth.domain.model;

import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
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
 * Entity representing an 'auth event' in the system, for sign up and sign in management.
 * <p>
 * Uses Lombok and JPA annotations for persistence and auditing.
 *
 * @author Giovane Neves
 */
@Table(name = "auth_event")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthEvent extends PersistenceEntity {

    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;

    @Column(name = "event_type", nullable = false)
    String eventType;

    @Column(name = "actor_type", nullable = false)
    String actorType;

    @Column(name = "actor_id", nullable = false)
    UUID actorId;

    @Column(name = "provider", nullable = false)
    String provider;

    @Column(name = "ip", nullable = false)
    String ip;

    @Column(name = "user_agent", nullable = false)
    String userAgent;

    @Column(name = "correlation_id", nullable = false)
    UUID correlationId;

    // TODO: Incluir biblioteca 'GSON'
    //GSON meta;

}
