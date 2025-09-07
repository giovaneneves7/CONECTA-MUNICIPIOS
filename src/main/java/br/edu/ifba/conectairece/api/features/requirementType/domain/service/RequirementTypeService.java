package br.edu.ifba.conectairece.api.features.requirementType.domain.service;

import java.util.List;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.request.RequirementTypeRequestDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.response.RequirementTypeResponseDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;
import br.edu.ifba.conectairece.api.features.requirementType.domain.repository.RequirementTypeRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for managing {@link RequirementType} entities.
 * Handles persistence and retrieval of requirement type definitions,
 * ensuring consistent classification of requirements.
 *
 * Main features:
 * - Save new requirement types (name and description).
 * - Retrieve all requirement types.
 * - Find requirement type by identifier.
 * - Delete requirement type by identifier.
 *
 * This service provides an abstraction over repository access,
 * exposing DTOs to maintain separation between persistence and API layers.
 *
 * Author: Caio Alves
 */

@Service
@RequiredArgsConstructor
public class RequirementTypeService implements RequirementTypeIService{

    private final RequirementTypeRepository repository;
    private final ObjectMapperUtil objectMapperUtil;

        /**
     * Saves a new requirement type in the database.
     *
     * @param dto request data containing requirement type details
     * @return DTO with saved requirement type information
     */

     @Override
    public RequirementTypeResponseDTO save(RequirementTypeRequestDTO dto) {
        RequirementType entity = new RequirementType();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        repository.save(entity);
        return objectMapperUtil.map(entity, RequirementTypeResponseDTO.class);
    }

    /**
     * Retrieves all requirement types.
     *
     * @return list of requirement type DTOs
     */
    @Override
    public List<RequirementTypeResponseDTO> findAll() {
        List<RequirementType> types = repository.findAll();
        return objectMapperUtil.mapAll(types, RequirementTypeResponseDTO.class);
    }

    /**
     * Finds a requirement type by its identifier.
     *
     * @param id requirement type ID
     * @return DTO containing requirement type data
     */
    @Override
    public RequirementTypeResponseDTO findById(Integer id) {
        RequirementType entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return objectMapperUtil.map(entity, RequirementTypeResponseDTO.class);
    }

    /**
     * Deletes a requirement type by its identifier.
     *
     * @param id requirement type ID
     */
    @Override
    public void delete(Integer id) {
        RequirementType entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        repository.delete(entity);
    }
}
