package br.edu.ifba.conectairece.api.features.category.domain.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.category.domain.dto.request.CategoryRequestDto;
import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.features.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
public class CategoryService implements CategoryIService{

    private final CategoryRepository categoryRepository;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Saves a new category in the database.
     *
     * @param dto request data containing category details
     * @return DTO with saved category information
     */
     @Override
     @Transactional
     @CacheEvict(value = "categories", allEntries = true)
     public CategoryResponseDto save(CategoryRequestDto dto) {
        if (categoryRepository.findByName(dto.name()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        return objectMapperUtil.mapToRecord(this.categoryRepository.save(this.objectMapperUtil.map(dto, Category.class)), CategoryResponseDto.class);

    }

    /**
    * Retrieves all categories from the database.
    *
    * @return list of category DTOs
    * */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categories")
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    /**
     * Finds a category by its identifier.
     *
     * @param id category ID
     * @return optional containing category DTO if found
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "category", key = "#id")
    public Optional<CategoryResponseDto> findById(Integer id) {
        return Optional.ofNullable(categoryRepository.findById(id)
                .map(category -> objectMapperUtil.mapToRecord(category, CategoryResponseDto.class))
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())));
    }

    /**
    * Deletes a category by its identifier.
    *
    * @param id category ID
    */
    @Override
    @Transactional
    @CacheEvict(value = {"categories", "category"}, key = "#id", allEntries = true)
    public void delete(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new  BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));


        if (!category.getMunicipalServices().isEmpty()) {
            throw new BusinessException(BusinessExceptionMessage.CLASS_IN_USE.getMessage());
        }

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
