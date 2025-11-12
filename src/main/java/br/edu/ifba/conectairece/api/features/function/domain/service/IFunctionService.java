package br.edu.ifba.conectairece.api.features.function.domain.service;

import br.edu.ifba.conectairece.api.features.function.domain.dto.response.FunctionResponseDTO;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Function} entities.
 * <p>
 * Defines the contract for CRUD operations related to functions.
 * </p>
 *
 * @author Jorge Roberto
 */
public interface IFunctionService {

    /**
     * Saves a new function in the database.
     *
     * @param function the {@link Function} entity containing function data
     * @return the {@link FunctionResponseDTO} representing the saved function
     * @throws BusinessException if a function with the same name already exists
     */
    FunctionResponseDTO save(Function function);

    /**
     * Updates an existing function in the database.
     *
     * @param function the {@link Function} entity containing updated function data
     * @return the {@link FunctionResponseDTO} representing the updated function
     * @throws BusinessException if no function exists with the given ID
     * @throws BusinessException if another function already has the same name
     */
    FunctionResponseDTO update(Function function);

    /**
     * Finds a function by its identifier.
     *
     * @param id the function ID
     * @return the {@link Function} entity
     * @throws BusinessException if no function exists with the given ID
     */
    Function findById(Long id);

    /**
     * Deletes a function by its identifier.
     *
     * @param id the function ID
     * @throws BusinessException if no function exists with the given ID
     * @throws BusinessException if the function is currently linked to one or more public servant profiles
     */
    void delete(Long id);

    /**
     * Retrieves a paginated list of projected {@link FunctionProjection} entities.
     *
     * @param pageable the pagination and sorting information
     * @return a {@link Page} containing projected functions
     */
    Page<FunctionProjection> findAllProjectedBy(Pageable pageable);
}
