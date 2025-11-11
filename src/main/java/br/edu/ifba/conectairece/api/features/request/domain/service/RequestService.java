package br.edu.ifba.conectairece.api.features.request.domain.service;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository.IConstructionLicenseRequirementRepository;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentWithStatusResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.repository.IMonitoringRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.IProfileRepository;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseWithDetailsDTO;
import br.edu.ifba.conectairece.api.features.request.domain.event.RequestCreatedEvent;
import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
import br.edu.ifba.conectairece.api.features.update.domain.repository.IUpdateRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.IMunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.IRequestRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
 * @author Caio Alves, Giovane Neves
 */

@Service
@RequiredArgsConstructor
public class RequestService implements RequestIService {

    private final IRequestRepository requestRepository;
    private final IMunicipalServiceRepository municipalServiceRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IProfileRepository profileRepository;
    private final IMonitoringRepository monitoringRepository;
    private final IUpdateRepository updateRepository;
    private final ApplicationEventPublisher eventPublisher;


    /**
     * Saves a new request for a given municipal service.
     *
     * @param dto request data containing protocol number, type, and related service ID
     * @return DTO with saved request information
     */
    @Override
    public RequestResponseDto save(final RequestPostRequestDto dto){

        MunicipalService service = municipalServiceRepository.findById(dto.municipalServiceId())
        .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Profile profile = profileRepository.findById(dto.profileId())
                .orElseThrow(() -> new BusinessException("Profile not found"));
        Request request = new Request();
        request.setProtocolNumber(dto.protocolNumber());
        request.setEstimatedCompletionDate(dto.estimatedCompletionDate());
        request.setType(dto.type());
        request.setNote(dto.note());
        request.setMunicipalService(service);
        request.setProfile(profile);

        Request saved = requestRepository.save(request);

        eventPublisher.publishEvent(new RequestCreatedEvent(saved));

        return objectMapperUtil.mapToRecord(request, RequestResponseDto.class);
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

        request.setProtocolNumber(dto.protocolNumber());
        request.setEstimatedCompletionDate(dto.estimatedCompletionDate());
        request.setType(dto.type());
        request.setNote(dto.note());

        MunicipalService service = municipalServiceRepository.findById(dto.municipalServiceId())
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

        return requests.stream()
                .map(request -> this.objectMapperUtil.mapToRecord(request, RequestResponseDto.class))
                .toList();

    }

    /**
     * Finds a request by its identifier.
     *
     * @param id request ID
     * @return optional containing request DTO if found
     */

     @Override
     public RequestResponseDto findById(final UUID id) {

         return requestRepository.findById(id)
                 .map(request -> this.objectMapperUtil.mapToRecord(request, RequestResponseDto.class))
                 .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

     }

     /**
     * Deletes a request by its identifier.
     *
     * @param id request ID
     */

     @Override
    public void delete(UUID id) {
       Request request = this.requestRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
       requestRepository.delete(request);
    }

    @Override
    public Page<MonitoringResponseDTO> getMonitoringListByRequestId(UUID id, Pageable pageable) {

         return this.monitoringRepository.findAllByRequestId(id, pageable)
                 .map(monitoring -> this.objectMapperUtil.mapToRecord(monitoring, MonitoringResponseDTO.class));

    }

    @Override
    public Page<UpdateResponseDTO> getUpdateListByRequestId(UUID id, Pageable pageable) {


         return this.updateRepository.findAllByRequestId(id, pageable)
                 .map(update -> this.objectMapperUtil.mapToRecord(update, UpdateResponseDTO.class));

    }

    /**
     * Finds and retrieves a paginated list of requests filtered by the specified type.
     * It uses the repository to fetch the data and maps the entities to DTOs.
     *
     * @param type The type used to filter the requests.
     * @param pageable The pagination and sorting parameters.
     * @return A Page object containing the filtered and paginated RequestResponseDto list.
     * @author Caio Alves
     */
    @Override
    public Page<RequestResponseDto> findByType(String type, Pageable pageable) {
        Page<Request> requestPage = requestRepository.findByType(type, pageable);
        return requestPage.map(request -> objectMapperUtil.mapToRecord(request, RequestResponseDto.class));
    }

    @Override @Transactional(readOnly = true)
    public Page<RequestResponseDto> findAllByStatusHistory_NewStatus(String statusHistoryNewStatus, Pageable pageable) {
        Page<Request> requestsPage = requestRepository.findAllByStatusHistory_NewStatus(statusHistoryNewStatus, pageable);
        return requestsPage.map(
                request -> this.objectMapperUtil.mapToRecord(
                        request, RequestResponseDto.class
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestResponseWithDetailsDTO> findAllFinalizedRequests(Pageable pageable) {

        Page<Request> requestsPage = requestRepository.findAllByStatusHistory_NewStatusIn(
                List.of("COMPLETE", "REJECTED"),
                pageable
        );

        return requestsPage.map(request -> {
            String statusValue = null;
            if (request.getStatusHistory() != null) {
                statusValue = request.getStatusHistory().getNewStatus();
            }

            RequestResponseWithDetailsDTO baseDto = objectMapperUtil.mapToRecord(
                    request,
                    RequestResponseWithDetailsDTO.class
            );

            return new RequestResponseWithDetailsDTO(
                    baseDto.id(),
                    baseDto.protocolNumber(),
                    baseDto.createdAt(),
                    baseDto.estimatedCompletionDate(),
                    baseDto.updatedAt(),
                    baseDto.type(),
                    baseDto.note(),
                    baseDto.municipalServiceId(),
                    statusValue,
                    request.getProfile().getUser().getPerson().getFullName(),
                    request.getProfile().getUser().getPerson().getCpf()
            );
        });
    }

    @Override
    public List<DocumentWithStatusResponseDTO> findApprovedDocumentsByRequestId(UUID requestId) {
        return List.of();
    }

    @Override
    public List<DocumentWithStatusResponseDTO> findAllDocumentsByRequestId(UUID requestId) {
        return List.of();
    }
}
