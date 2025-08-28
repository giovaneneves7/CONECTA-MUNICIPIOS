package br.edu.ifba.conectairece.api.features.category.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.category.domain.dto.request.CategoryRequestDto;
import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.features.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for managing {@link Category} entities.
 * Handles creation, retrieval, and deletion of categories,
 * and converts entities to their respective DTO representations.
 *
 * Main features:
 * - Save new categories
 * - Find all categories or by ID
 * - Delete categories by ID
 *
 * @author Caio Alves
 */

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

      /**
     * Saves a new category in the database.
     *
     * @param dto request data containing category details
     * @return DTO with saved category information
     */

     public CategoryResponseDto save(CategoryRequestDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());

        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

      /**
     * Retrieves all categories from the database.
     *
     * @return list of category DTOs
     */

    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    /**
     * Finds a category by its identifier.
     *
     * @param id category ID
     * @return optional containing category DTO if found
     */

    public Optional<CategoryResponseDto> findById(Integer id) {
        return categoryRepository.findById(id).map(this::toDto);
    }

     /**
     * Deletes a category by its identifier.
     *
     * @param id category ID
     */

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDto toDto(Category entity) {
        return new CategoryResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl()
        );
    }
}
