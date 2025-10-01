package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;

/**
 * Data access repository for the {@link TechnicalResponsible} entity.
 * Provides CRUD operations and database interaction methods.
 *
 * @author Caio Alves
 */
@Repository
public interface TechnicalResponsibleRepository extends JpaRepository<TechnicalResponsible, UUID> {
    Optional<TechnicalResponsible> findByRegistrationId(String registrationId);

}
