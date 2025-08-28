package br.edu.ifba.conectairece.api.features.function.domain.service;

import br.edu.ifba.conectairece.api.features.function.domain.dto.response.FunctionResponseDTO;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
import br.edu.ifba.conectairece.api.infraestructure.exception.custom.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Function} entities.
 * Defines the contract for CRUD operations on functions.
 *
 * @author Jorge Roberto
 */
public interface FunctionIService {
    /**
     * Persists a new {@link Function}.
     *
     * @param function the {@link Function} entity to save
     * @return a {@link FunctionResponseDTO} representing the saved function
     */
    FunctionResponseDTO save(Function function);

    /**
     * Updates an existing {@link Function} entity in the database.
     *
     * <p>If the entity with the provided ID does not exist, an
     * {@link EntityNotFoundException} is thrown.</p>
     *
     * @param function the {@link Function} containing updated data
     * @return the updated {@link FunctionResponseDTO} representation
     * @throws EntityNotFoundException if the function with given ID does not exist
     */
    FunctionResponseDTO update(Function function);

    /**
     * Finds a {@link Function} entity by its ID.
     *
     * @param id the ID of the function
     * @return the {@link Function} entity
     * @throws EntityNotFoundException if no function with the given ID exists
     */
    Function findById(Long id);

    /**
     * Deletes a {@link Function} entity by its ID.
     *
     * <p>If the entity with the provided ID does not exist, an
     * {@link EntityNotFoundException} is thrown.</p>
     *
     * @param id the ID of the {@link Function} to delete
     * @throws EntityNotFoundException if the function with given ID does not exist
     */
    void delete(Long id);

    /**
     * Retrieves a paginated list of {@link FunctionProjection} by
     * delegating the query to the repository.
     *
     * @param pageable the pagination information (page number, size, sorting)
     * @return a page containing projected functions
     */
    Page<FunctionProjection> findAllProjectedBy(Pageable pageable);
}
