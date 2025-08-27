package br.edu.ifba.conectairece.api.features.municipal_service.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.features.category.domain.repository.CategoryRepository;
import br.edu.ifba.conectairece.api.features.municipal_service.domain.dto.request.MunicipalServiceRequestDto;
import br.edu.ifba.conectairece.api.features.municipal_service.domain.dto.response.MunicipalServiceResponseDto;
import br.edu.ifba.conectairece.api.features.municipal_service.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipal_service.domain.repository.MunicipalServiceRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for managing {@link MunicipalService} entities.
 * Handles creation, retrieval, and deletion of municipal services,
 * and maps services to their associated categories.
 *
 * Main features:
 * - Save new municipal services with categories
 * - Find all services or by ID
 * - Delete services by ID
 *
 * @author Caio Alves
 */

@Service
@RequiredArgsConstructor
public class MunicipalServiceService {

    private final MunicipalServiceRepository municipalServiceRepository;
    private final CategoryRepository categoryRepository;

     /**
     * Saves a new municipal service in the database.
     *
     * @param dto request data containing service details and category IDs
     * @return DTO with saved municipal service information
     */

    public MunicipalServiceResponseDto save(MunicipalServiceRequestDto dto) {
        MunicipalService service = new MunicipalService();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());

        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            service.setCategories(categories);
        }

        MunicipalService saved = municipalServiceRepository.save(service);
        return toDto(saved);
    }

    /**
     * Retrieves all municipal services.
     *
     * @return list of municipal service DTOs
     */

    public List<MunicipalServiceResponseDto> findAll() {
        return municipalServiceRepository.findAll().stream().map(this::toDto).toList();
    }

     /**
     * Finds a municipal service by its identifier.
     *
     * @param id service ID
     * @return optional containing municipal service DTO if found
     */

    public Optional<MunicipalServiceResponseDto> findById(Integer id) {
        return municipalServiceRepository.findById(id).map(this::toDto);
    }

     /**
     * Deletes a municipal service by its identifier.
     *
     * @param id service ID
     */

    public void delete(Integer id) {
        municipalServiceRepository.deleteById(id);
    }

     private MunicipalServiceResponseDto toDto(MunicipalService entity) {
        List<CategoryResponseDto> categoryDtos = entity.getCategories().stream()
                .map(c -> new CategoryResponseDto(c.getId(), c.getName(), c.getDescription(), c.getImageUrl()))
                .toList();

        return new MunicipalServiceResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                categoryDtos
        );
    }


}
