package br.edu.ifba.conectairece.api.features.function.domain.service;

import br.edu.ifba.conectairece.api.features.function.domain.dto.response.FunctionResponseDTO;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
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
     * Updates an existing {@link Function}.
     *
     * @param function the {@link Function} entity with updated fields
     * @return a {@link FunctionResponseDTO} representing the updated function
     */
    FunctionResponseDTO update(Function function);

    /**
     * Finds a {@link Function} by its ID.
     *
     * @param id the ID of the {@link Function}
     * @return the found {@link Function}
     */
    Function findById(Long id);

    /**
     * Deletes a {@link Function} by its ID.
     *
     * @param id the ID of the {@link Function} to delete
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
