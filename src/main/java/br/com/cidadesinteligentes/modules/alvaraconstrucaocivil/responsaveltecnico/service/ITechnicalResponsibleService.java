package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.request.TechnicalResponsibleRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response.TechnicalResponsibleResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model.TechnicalResponsible;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link TechnicalResponsible} entities.
 * Defines the contract for CRUD operations related to technical responsibles.
 *
 * @author Caio Alves
 */
public interface ITechnicalResponsibleService {

    TechnicalResponsibleResponseDTO save(final TechnicalResponsibleRequestDTO dto);

    List<TechnicalResponsibleResponseDTO> findAll(final Pageable pageable);

    Optional<TechnicalResponsibleResponseDTO> findById(final UUID id);

    void delete(UUID id);

    Optional<TechnicalResponsibleResponseDTO> findByRegistrationId(final String registrationId);
}
