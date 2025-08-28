package br.edu.ifba.conectairece.api.features.request.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDto;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
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
public class RequestService {

    private final RequestRepository requestRepository;
    private final MunicipalServiceRepository municipalServiceRepository;

    /**
     * Saves a new request for a given municipal service.
     *
     * @param dto request data containing protocol number, type, and related service ID
     * @return DTO with saved request information
     */

    public RequestResponseDto save(RequestPostRequestDto dto){
        MunicipalService service = municipalServiceRepository.findById(dto.getMunicipalServiceId())
        .orElseThrow(() -> new RuntimeException("MunicipalService not found"));

        Request request = new Request();
        request.setProtocolNumber(dto.getProtocolNumber());
        request.setEstimatedCompletionDate(dto.getEstimatedCompletionDate());
        request.setType(dto.getType());
        request.setNote(dto.getNote());
        request.setMunicipalService(service);

        Request saved = requestRepository.save(request);

        return toDto(saved);
    }

    /**
     * Updates an existing request in the database.
     *
     * @param id identifier of the request to update
     * @param dto data containing updated request details
     * @return DTO with updated request information
     */

     public RequestResponseDto update(UUID id, RequestPostRequestDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setProtocolNumber(dto.getProtocolNumber());
        request.setEstimatedCompletionDate(dto.getEstimatedCompletionDate());
        request.setType(dto.getType());
        request.setNote(dto.getNote());

        MunicipalService service = municipalServiceRepository.findById(dto.getMunicipalServiceId())
                .orElseThrow(() -> new RuntimeException("MunicipalService not found"));
        request.setMunicipalService(service);

        Request updated = requestRepository.save(request);
        return toDto(updated);
    }

     /**
     * Retrieves all requests from the database.
     *
     * @return list of request DTOs
     */

    public List<RequestResponseDto> findAll(){
        return requestRepository.findAll().stream().map(this::toDto).toList();
    }

    /**
     * Finds a request by its identifier.
     *
     * @param id request ID
     * @return optional containing request DTO if found
     */

      public Optional<RequestResponseDto> findById(UUID id) {
        return requestRepository.findById(id).map(this::toDto);
    }

     /**
     * Deletes a request by its identifier.
     *
     * @param id request ID
     */

    public void delete(UUID id) {
        requestRepository.deleteById(id);
    }

    private RequestResponseDto toDto(Request entity) {
        MunicipalServiceResponseDto serviceDto = new MunicipalServiceResponseDto(
                entity.getMunicipalService().getId(),
                entity.getMunicipalService().getName(),
                entity.getMunicipalService().getDescription(),
                new ArrayList<>()
        );

        return new RequestResponseDto(
                entity.getId(),
                entity.getProtocolNumber(),
                entity.getCreatedAt(),
                entity.getEstimatedCompletionDate(),
                entity.getUpdatedAt(),
                entity.getType(),
                entity.getNote(),
                serviceDto
        );
    }
}
