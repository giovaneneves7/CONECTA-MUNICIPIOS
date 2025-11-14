package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;

/**
 * Service interface for managing {@link TechnicalResponsible} entities.
 * Defines the contract for CRUD operations related to technical responsibles.
 *
 * @author Caio Alves
 */
public interface ITechnicalResponsibleService {

    TechnicalResponsibleResponseDTO_TEMP save(TechnicalResponsibleRequestDTO_TEMP dto);

    List<TechnicalResponsibleResponseDTO_TEMP> findAll();

    Optional<TechnicalResponsibleResponseDTO_TEMP> findById(UUID id);

    void delete(UUID id);

    Optional<TechnicalResponsibleResponseDTO_TEMP> findByRegistrationId(String registrationId);
}
