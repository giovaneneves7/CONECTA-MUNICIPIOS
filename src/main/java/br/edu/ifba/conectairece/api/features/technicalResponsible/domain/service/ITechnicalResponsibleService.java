package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;

/**
 * Service interface for managing {@link TechnicalResponsible} entities.
 * Defines the contract for CRUD operations related to technical responsibles.
 *
 * @author Caio Alves
 */
public interface ITechnicalResponsibleService {

    TechnicalResponsibleResponseDto save(TechnicalResponsibleRequestDto dto);

    List<TechnicalResponsibleResponseDto> findAll();

    Optional<TechnicalResponsibleResponseDto> findById(UUID id);

    void delete(UUID id);

    Optional<TechnicalResponsibleResponseDto> findByRegistrationId(String registrationId);
}
