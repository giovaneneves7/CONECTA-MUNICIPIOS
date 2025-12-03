package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.repository.IConstructionLicenseRequirementRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentWithStatusResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums.DocumentStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.MonitoringResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.repository.IMonitoringRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.event.RequestCreatedEvent;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.UpdateResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.repository.IUpdateRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IServicoMunicipalRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.RequestPostRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.RequestUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseWithDetailsDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.IRequestRepository;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
 * @author Caio Alves, Giovane Neves, Andesson Reis
 */

@Service
@RequiredArgsConstructor
public class RequestService implements IRequestService {

    private final IRequestRepository requestRepository;
    private final IServicoMunicipalRepository municipalServiceRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IPerfilRepository profileRepository;
    private final IMonitoringRepository monitoringRepository;
    private final IUpdateRepository updateRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final IConstructionLicenseRequirementRepository requirementRepository;

    /**
     * Saves a new request for a given municipal service.
     *
     * @param dto request data containing protocol number, type, and related service
     *            ID
     * @return DTO with saved request information
     */
    @Override
    public RequestResponseDTO save(final RequestPostRequestDTO dto) {

        ServicoMunicipal service = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Perfil profile = profileRepository.findById(dto.profileId())
                .orElseThrow(() -> new BusinessException("Profile not found"));
        Request request = new Request();
        request.setProtocolNumber(dto.protocolNumber());
        request.setEstimatedCompletionDate(dto.estimatedCompletionDate());
        request.setType(dto.type());
        request.setNote(dto.note());
        request.setServicoMunicipal(service);
        request.setProfile(profile);

        Request saved = requestRepository.save(request);

        eventPublisher.publishEvent(new RequestCreatedEvent(saved));

        return objectMapperUtil.mapToRecord(request, RequestResponseDTO.class);
    }

    /**
     * Updates an existing request in the database.
     *
     * @param id  identifier of the request to update
     * @param dto data containing updated request details
     * @return DTO with updated request information
     */
    @Override
    public RequestResponseDTO update(UUID id, RequestUpdateRequestDTO dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        request.setProtocolNumber(dto.protocolNumber());
        request.setEstimatedCompletionDate(dto.estimatedCompletionDate());
        request.setType(dto.type());
        request.setNote(dto.note());

        ServicoMunicipal service = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        request.setServicoMunicipal(service);

        requestRepository.save(request);
        return objectMapperUtil.map(request, RequestResponseDTO.class);
    }

    /**
     * Retrieves all requests from the database.
     *
     * @return list of request DTOs
     */

    @Override
    public List<RequestResponseDTO> findAll(final Pageable pageable) {

        Page<Request> requests = requestRepository.findAll(pageable);

        return requests.stream()
                .map(request -> this.objectMapperUtil.mapToRecord(request, RequestResponseDTO.class))
                .toList();

    }

    /**
     * Finds a request by its identifier.
     *
     * @param id request ID
     * @return optional containing request DTO if found
     */

    @Override
    public RequestResponseDTO findById(final UUID id) {

        return requestRepository.findById(id)
                .map(request -> this.objectMapperUtil.mapToRecord(request, RequestResponseDTO.class))
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

    }

    /**
     * Deletes a request by its identifier.
     *
     * @param id request ID
     */

    @Override
    public void delete(UUID id) {
        Request request = this.requestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
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
     * Finds and retrieves a paginated list of requests filtered by the specified
     * type.
     * It uses the repository to fetch the data and maps the entities to DTOs.
     *
     * @param type     The type used to filter the requests.
     * @param pageable The pagination and sorting parameters.
     * @return A Page object containing the filtered and paginated
     *         RequestResponseDto list.
     * @author Caio Alves
     */
    @Override
    public Page<RequestResponseDTO> findByType(String type, Pageable pageable) {
        Page<Request> requestPage = requestRepository.findByType(type, pageable);
        return requestPage.map(request -> objectMapperUtil.mapToRecord(request, RequestResponseDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestResponseDTO> findAllByStatusHistory_NewStatus(String statusHistoryNewStatus, Pageable pageable) {
        Page<Request> requestsPage = requestRepository.findAllByStatusHistory_NewStatus(statusHistoryNewStatus,
                pageable);
        return requestsPage.map(
                request -> this.objectMapperUtil.mapToRecord(
                        request, RequestResponseDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestResponseWithDetailsDTO> findAllFinalizedRequests(Pageable pageable) {

        Page<Request> requestsPage = requestRepository.findAllByStatusHistory_NewStatusIn(
                List.of("COMPLETE", "REJECTED"),
                pageable);

        return requestsPage.map(request -> {
            String statusValue = null;
            if (request.getStatusHistory() != null) {
                statusValue = request.getStatusHistory().getNewStatus();
            }

            RequestResponseWithDetailsDTO baseDto = objectMapperUtil.mapToRecord(
                    request,
                    RequestResponseWithDetailsDTO.class);

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
                    request.getProfile().getUsuario().getPessoa().getNomeCompleto(),
                    request.getProfile().getUsuario().getPessoa().getCpf());
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentWithStatusResponseDTO> findApprovedDocumentsByRequestId(UUID requestId) {
        Request request = requestRepository
                .findById(requestId)
                .orElseThrow(() -> new BusinessException("Request not found with ID: " + requestId));

        ServicoMunicipal municipalService = request.getServicoMunicipal();
        if (municipalService == null) {
            throw new BusinessException("Request não está associado a um Serviço Municipal.");
        }

        ConstructionLicenseRequirement requirement = requirementRepository
                .findById(municipalService.getId())
                .orElseThrow(() -> new BusinessException(
                        "Nenhum Requirement encontrado para o Serviço Municipal ID: " + municipalService.getId()));

        return requirement
                .getDocuments()
                .stream()
                .filter(document -> document.getStatus() == DocumentStatus.APPROVED)
                .map(
                        doc -> new DocumentWithStatusResponseDTO(doc.getId(), doc.getName(), doc.getFileExtension(),
                                doc.getFileUrl(), doc.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentWithStatusResponseDTO> findAllDocumentsByRequestId(UUID requestId) {

        Request request = requestRepository
                .findById(requestId)
                .orElseThrow(() -> new BusinessException("Request not found with ID: " + requestId));

        ServicoMunicipal municipalService = request.getServicoMunicipal();
        if (municipalService == null) {
            throw new BusinessException("Request não está associado a um Serviço Municipal.");
        }

        ConstructionLicenseRequirement requirement = requirementRepository
                .findById(municipalService.getId())
                .orElseThrow(() -> new BusinessException(
                        "Nenhum Requirement encontrado para o Serviço Municipal ID: " + municipalService.getId()));

        return requirement
                .getDocuments()
                .stream()
                .map(doc -> new DocumentWithStatusResponseDTO(doc.getId(), doc.getName(), doc.getFileExtension(),
                        doc.getFileUrl(), doc.getStatus()))
                .collect(Collectors.toList());
    }
}
