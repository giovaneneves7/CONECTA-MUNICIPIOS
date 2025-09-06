package br.edu.ifba.conectairece.api.features.municipalservice.domain.service;

import java.util.List;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.features.category.domain.repository.CategoryRepository;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request.MunicipalServiceRequestDto;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDto;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
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
public class MunicipalServiceService implements MunicipalServiceIService{

    private final ObjectMapperUtil objectMapperUtil;

    private final MunicipalServiceRepository municipalServiceRepository;
    private final CategoryRepository categoryRepository;

     /**
     * Saves a new municipal service in the database.
     *
     * @param dto request data containing service details and category IDs
     * @return DTO with saved municipal service information
     */

    @Override
    public MunicipalServiceResponseDto save(MunicipalServiceRequestDto dto) {
        MunicipalService service = new MunicipalService();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());

        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            service.setCategories(categories);
        }

        municipalServiceRepository.save(service);
        return objectMapperUtil.map(service, MunicipalServiceResponseDto.class);
    }

    /**
     * Retrieves all municipal services.
     *
     * @return list of municipal service DTOs
     */

    @Override
    public List<MunicipalServiceResponseDto> findAll() {
        List<MunicipalService> services = municipalServiceRepository.findAll();
        return objectMapperUtil.mapAll(services, MunicipalServiceResponseDto.class);
    }

     /**
     * Finds a municipal service by its identifier.
     *
     * @param id service ID
     * @return optional containing municipal service DTO if found
     */
    @Override
    public MunicipalServiceResponseDto findById(Integer id) {
       MunicipalService entity = municipalServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Municipal Service not found"));

        return objectMapperUtil.map(entity, MunicipalServiceResponseDto.class);
        
    }

     /**
     * Deletes a municipal service by its identifier.
     *
     * @param id service ID
     */

     @Override
    public void delete(Integer id) {
        MunicipalService entity = municipalServiceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Municipal Service not found"));

        municipalServiceRepository.delete(entity);
    }

}
