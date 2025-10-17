package br.edu.ifba.conectairece.api.features.requirement.domain.repository;

import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Requirement} entities.
 * <p>
 * Provides access to persistence operations related to requirements,
 * leveraging Spring Data JPA's {@link JpaRepository} to simplify data access.
 * This layer abstracts database operations, allowing the domain and service
 * layers to remain persistence-agnostic.
 * </p>
 *
 * @author Andesson Reis
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement
 */
@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Long> {
}
