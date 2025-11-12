package br.edu.ifba.conectairece.api.features.category.domain.service;

import br.edu.ifba.conectairece.api.features.category.domain.dto.request.CategoryRequestDto;
import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Category} entities.
 * <p>
 * Defines the contract for CRUD operations related to categories.
 * </p>
 *
 * @author Jorge Roberto
 */
public interface ICategoryService {

    /**
     * Saves a new category in the database.
     *
     * @param dto the {@link CategoryRequestDto} containing category data
     * @return the {@link CategoryResponseDto} representing the saved category
     * @throws BusinessException if a category with the same name already exists
     */
    CategoryResponseDto save(CategoryRequestDto dto);

    /**
     * Retrieves all categories from the database.
     *
     * @return list of {@link CategoryResponseDto}
     */
    List<CategoryResponseDto> findAll();

    /**
     * Finds a category by its identifier.
     *
     * @param id the category ID
     * @return an {@link Optional} containing {@link CategoryResponseDto} if found
     * @throws BusinessException if no category exists with the given ID
     */
    Optional<CategoryResponseDto> findById(Integer id);

    /**
     * Deletes a category by its identifier.
     *
     * @param id the category ID
     * @throws BusinessException if no category exists with the given ID
     * @throws BusinessException if the category is associated with one or more municipal services
     */
    void delete(Integer id);
}
