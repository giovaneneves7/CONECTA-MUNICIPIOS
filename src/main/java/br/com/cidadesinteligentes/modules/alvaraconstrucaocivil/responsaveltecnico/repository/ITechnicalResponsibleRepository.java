package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model.TechnicalResponsible;

/**
 * Data access repository for the {@link TechnicalResponsible} entity.
 * Provides CRUD operations and database interaction methods.
 *
 * @author Caio Alves
 */
@Repository
public interface ITechnicalResponsibleRepository extends JpaRepository<TechnicalResponsible, UUID> {
    Optional<TechnicalResponsible> findByRegistrationId(String registrationId);

}
