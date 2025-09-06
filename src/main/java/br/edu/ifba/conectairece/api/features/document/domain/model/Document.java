package br.edu.ifba.conectairece.api.features.document.domain.model;

import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a document associated with a {@link Requirement}.
 * Stores metadata about uploaded files, such as:
 * - File name.
 * - File extension.
 * - File storage URL.
 *
 * Relationships:
 * - Many documents can belong to one requirement.
 *
 * This class is useful for managing and tracking supporting documentation
 * required by different municipal services and their respective requirements.
 * 
 * Author: Caio Alves
 */

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends PersistenceEntity{

    private String name;
    private String fileExtension;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

}
