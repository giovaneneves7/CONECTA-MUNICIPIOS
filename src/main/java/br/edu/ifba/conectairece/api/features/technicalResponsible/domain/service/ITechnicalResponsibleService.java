package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;

/**
 * Service interface for managing {@link TechnicalResponsible} entities.
 * Defines the contract for CRUD operations related to technical responsibles.
 *
 * @author Caio Alves
 */
public interface ITechnicalResponsibleService {

    TechnicalResponsibleResponseDTO save(TechnicalResponsibleRequestDTO dto);

    List<TechnicalResponsibleResponseDTO> findAll();

    Optional<TechnicalResponsibleResponseDTO> findById(UUID id);

    void delete(UUID id);

    Optional<TechnicalResponsibleResponseDTO> findByRegistrationId(String registrationId);
}
