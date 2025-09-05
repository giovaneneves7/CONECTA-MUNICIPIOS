package br.edu.ifba.conectairece.api.features.request.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.stereotype.Service;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for managing {@link Request} entities.
 * Handles creation, updating, retrieval, and deletion of service requests,
 * and maps them to their respective DTOs.
 *
 * Main features:
 * - Save and update requests linked to a municipal service
 * - Retrieve all requests or find by ID
 * - Delete requests by ID
 *
 * @author Caio Alves
 */

@Service
@RequiredArgsConstructor
public class RequestService implements RequestIService {

    private final RequestRepository requestRepository;
    private final MunicipalServiceRepository municipalServiceRepository;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Saves a new request for a given municipal service.
     *
     * @param dto request data containing protocol number, type, and related service ID
     * @return DTO with saved request information
     */
    @Override
    public RequestResponseDto save(RequestPostRequestDto dto){
        MunicipalService service = municipalServiceRepository.findById(dto.getMunicipalServiceId())
        .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Request request = new Request();
        request.setProtocolNumber(dto.getProtocolNumber());
        request.setEstimatedCompletionDate(dto.getEstimatedCompletionDate());
        request.setType(dto.getType());
        request.setNote(dto.getNote());
        request.setMunicipalService(service);

        requestRepository.save(request);

        return objectMapperUtil.map(request, RequestResponseDto.class);
    }

    /**
     * Updates an existing request in the database.
     *
     * @param id identifier of the request to update
     * @param dto data containing updated request details
     * @return DTO with updated request information
     */
    @Override
     public RequestResponseDto update(UUID id, RequestPostRequestDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        request.setProtocolNumber(dto.getProtocolNumber());
        request.setEstimatedCompletionDate(dto.getEstimatedCompletionDate());
        request.setType(dto.getType());
        request.setNote(dto.getNote());

        MunicipalService service = municipalServiceRepository.findById(dto.getMunicipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        request.setMunicipalService(service);

        requestRepository.save(request);
        return objectMapperUtil.map(request, RequestResponseDto.class);
    }

     /**
     * Retrieves all requests from the database.
     *
     * @return list of request DTOs
     */

     @Override
    public List<RequestResponseDto> findAll(){
        List<Request> requests = requestRepository.findAll();

        return objectMapperUtil.mapAll(requests, RequestResponseDto.class);
    }

    /**
     * Finds a request by its identifier.
     *
     * @param id request ID
     * @return optional containing request DTO if found
     */

     @Override
      public Request findById(UUID id) {
        
        Optional<Request> request = requestRepository.findById(id);
        if(request.isEmpty()){
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }
        return request.get();
      }

     /**
     * Deletes a request by its identifier.
     *
     * @param id request ID
     */

     @Override
    public void delete(UUID id) {
       Request request = findById(id);
       requestRepository.delete(request);
    }

}
