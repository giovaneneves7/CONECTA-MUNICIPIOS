package br.edu.ifba.conectairece.api.features.comment.domain.model;

import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an official comment, note, or justification associated with a Requirement.
 * <p>
 * In the current context, this entity is used to store the formal justification (note)
 * provided by a public servant when accepting or rejecting a license request (Requirement).
 * It maintains a strictly one-to-one relationship with the {@link Requirement} it annotates.
 */
@Entity
@Table(name = "comments")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends PersistenceEntity {
    @Column(name = "note", nullable = false)
    private String note;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requirement_id", nullable = false, unique = true)
    private Requirement requirement;
}
