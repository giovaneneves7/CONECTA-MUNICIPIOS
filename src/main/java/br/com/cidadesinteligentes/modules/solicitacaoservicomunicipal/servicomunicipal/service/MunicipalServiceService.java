package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.service;

import java.util.List;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.request.MunicipalServiceRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IMunicipalServiceRepository;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
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
public class MunicipalServiceService implements IMunicipalServiceService {

    private final ObjectMapperUtil objectMapperUtil;

    private final IMunicipalServiceRepository municipalServiceRepository;

     /**
     * Saves a new municipal service in the database.
     *
     * @param dto request data containing service details and category IDs
     * @return DTO with saved municipal service information
     */

    @Override
    public MunicipalServiceResponseDTO save(MunicipalServiceRequestDTO dto) {
        MunicipalService service = new MunicipalService();
        service.setName(dto.name());
        service.setDescription(dto.description());

        MunicipalService saved = municipalServiceRepository.save(service);
        // força carregamento das categorias
        saved = municipalServiceRepository.findById(saved.getId())
                .orElseThrow(() -> new BusinessException("Service not found"));


        return new MunicipalServiceResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getDescription()
        );
    }

    /**
     * Retrieves all municipal services.
     *
     * @return list of municipal service DTOs
     */

    @Override
    public List<MunicipalServiceResponseDTO> findAll(final Pageable pageable) {
        Page<MunicipalService> services = municipalServiceRepository.findAll(pageable);

        return services.stream().map(service -> {
            return new MunicipalServiceResponseDTO(
                    service.getId(),
                    service.getName(),
                    service.getDescription()
            );
        }).toList();
    }

     /**
     * Finds a municipal service by its identifier.
     *
     * @param id service ID
     * @return optional containing municipal service DTO if found
     */
    @Override
    public MunicipalServiceResponseDTO findById(Long id) {
       MunicipalService entity = municipalServiceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return objectMapperUtil.mapToRecord(entity, MunicipalServiceResponseDTO.class);
        
    }

     /**
     * Deletes a municipal service by its identifier.
     *
     * @param id service ID
     */

     @Override
    public void delete(Long id) {
        MunicipalService entity = municipalServiceRepository.findById(id)
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        municipalServiceRepository.delete(entity);
    }

}
