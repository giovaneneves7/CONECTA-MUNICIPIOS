package br.edu.ifba.conectairece.api.features.municipalservice.domain.service;

import java.util.List;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request.MunicipalServiceRequestDTO;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDTO;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;

/**
 * Interface defining the contract for managing {@link MunicipalService} entities.
 * Provides methods for creating, retrieving, and deleting municipal services.
 *
 * @author Caio
 */
public interface IMunicipalServiceService {
 /**
     * Saves a new municipal service.
     *
     * @param dto request data containing service details and category IDs
     * @return DTO with saved municipal service information
     */
    MunicipalServiceResponseDTO save(MunicipalServiceRequestDTO dto);

    /**
     * Retrieves all municipal services.
     *
     * @return list of municipal service DTOs
     */
    List<MunicipalServiceResponseDTO> findAll();

    /**
     * Finds a municipal service by its identifier.
     *
     * @param id service ID
     * @return the found municipal service entity
     */
    MunicipalServiceResponseDTO findById(Long id);

    /**
     * Deletes a municipal service by its identifier.
     *
     * @param id service ID
     */
    void delete(Long id);

}
