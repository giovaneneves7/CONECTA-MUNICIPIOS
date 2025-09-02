package br.edu.ifba.conectairece.api.features.category.domain.service;

import br.edu.ifba.conectairece.api.features.category.domain.dto.request.CategoryRequestDto;
import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.infraestructure.exception.custom.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Category} entities.
 * Defines the contract for CRUD operations on functions.
 *
 * @author Jorge Roberto
 */
public interface CategoryIService {
    /**
     * Saves a new category in the database.
     *
     * @param dto the {@link Category} entity to save
     * @return the {@link CategoryResponseDto} representing the saved function
     */
    CategoryResponseDto save(CategoryRequestDto dto);

    /**
     * Retrieves all categories from the database.
     *
     * @return list of category DTOs
     */
    List<CategoryResponseDto> findAll();

    /**
     * Finds a category by its identifier.
     *
     * @param id category ID
     * @return optional containing category DTO if found
     * @throws EntityNotFoundException if the function with given ID does not exist
     */
    Optional<CategoryResponseDto> findById(Integer id);

    /**
     * Deletes a category by its identifier.
     *
     * @param id category ID
     * @throws EntityNotFoundException if the function with given ID does not exist
     */
    void delete(Integer id);
}
