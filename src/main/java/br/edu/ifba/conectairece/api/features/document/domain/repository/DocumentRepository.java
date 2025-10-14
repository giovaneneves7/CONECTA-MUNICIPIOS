package br.edu.ifba.conectairece.api.features.document.domain.repository;

import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface responsible for data persistence and retrieval operations for the {@link Document} entity.
 * <p>
 * This interface abstracts the underlying data storage mechanism (e.g., a SQL database) and
 * provides a clean, object-oriented API for interacting with the data. Spring Data JPA
 * will automatically implement the standard CRUD (Create, Read, Update, Delete) methods at runtime.
 * </p>
 * <p>
 * Custom query methods for finding documents based on specific criteria (e.g., by status or requirement)
 * can be defined here by following the Spring Data query creation conventions.
 * </p>
 *
 * @author Andesson Reis
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {}