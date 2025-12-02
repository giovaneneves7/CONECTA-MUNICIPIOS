package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.comentariorequerimento.model.Comment;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.comentariorequerimento.repository.ICommentRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.ConstructionLicenseRequirementFinalizeRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums.DocumentStatus;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.response.*;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service.IMonitoringService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.repository.IPublicServantProfileRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.IRequestRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimento.enums.RequirementStatus;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.AssociationActionRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.ConstructionLicenseRequirementUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.RejectionRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.enums.AssociationStatus;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.event.ConstructionLicenseRequirementCreatedEvent;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.repository.IConstructionLicenseRequirementRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.model.Document;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IMunicipalServiceRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfilePublicDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.model.RequirementType;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.repository.IRequirementTypeRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response.TechnicalResponsibleResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model.TechnicalResponsible;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.repository.ITechnicalResponsibleRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.constraints.NotNull;

/**
 * Service responsible for managing {@link ConstructionLicenseRequirement}
 * entities.
 * Implements business logic for handling construction license requests,
 * including persistence and validation of relationships.
 *
 * Main features:
 * - Save new construction license requirements with:
 * - Linked {@link MunicipalService}.
 * - Associated {@link RequirementType}.
 * - Assigned {@link TechnicalResponsible}.
 * - Attached {@link Document} list.
 * - Retrieve all construction license requirements.
 * - Find a requirement by its identifier.
 * - Delete a requirement by its identifier.
 *
 * This service ensures that all associations are properly mapped
 * and stored in the database, while exposing DTO-based responses for clients.
 *
 * Author: Caio Alves, Andesson Reis
 */

@Service
@RequiredArgsConstructor
public class ConstructionLicenseRequirementService implements IConstructionLicenseRequirementService {

    private final IMonitoringService monitoringService;

    private final IConstructionLicenseRequirementRepository repository;
    private final IMunicipalServiceRepository municipalServiceRepository;
    private final IRequirementTypeRepository requirementTypeRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final ITechnicalResponsibleRepository technicalResponsibleRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final IUsuarioRepository userRepository;
    private final IPerfilRepository profileRepository;
    private final IRequestRepository requestRepository;
    private final IPublicServantProfileRepository publicServantProfileRepository;
    private final ICommentRepository commentRepository;

    @Override
    @Transactional
    public ConstructionLicenseRequirementWithRequestIDResponseDTO save(ConstructionLicenseRequirementRequestDTO dto) {

        Perfil solicitanteProfile = profileRepository.findById(dto.solicitanteProfileId())
                .orElseThrow(() -> new BusinessException("Perfil do solicitante não encontrado"));

        Usuario solicitante = solicitanteProfile.getUser();

        ConstructionLicenseRequirement entity = objectMapperUtil.map(dto, ConstructionLicenseRequirement.class);

        entity.setSolicitante(solicitante);

        MunicipalService serviceToInherit = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        RequirementType type = requirementTypeRepository.findById(dto.requirementTypeId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setRequirementType(type);

        TechnicalResponsible responsible = technicalResponsibleRepository
                .findByRegistrationId(dto.technicalResponsibleRegistrationId())
                .orElseThrow(() -> new BusinessException("Responsável Técnico não encontrado com o registro: "
                        + dto.technicalResponsibleRegistrationId()));

        if (serviceToInherit.getFlow() != null) {
            // Como o CLR herda de MunicipalService, ele tem o campo 'flow'.
            entity.setFlow(serviceToInherit.getFlow());
        }
        entity.setTechnicalResponsible(responsible);
        entity.setName(serviceToInherit.getName());
        entity.setDescription(serviceToInherit.getDescription());
        entity.setTechnicalResponsibleStatus(AssociationStatus.PENDING);

        if (dto.documents() != null) {
            List<Document> docs = dto.documents().stream()
                    .map(d -> {
                        Document document = objectMapperUtil.map(d, Document.class);
                        document.setRequirement(entity);
                        return document;
                    })
                    .collect(Collectors.toList());
            entity.setDocuments(docs);
        }
        entity.setStatus(RequirementStatus.PENDING);
        ConstructionLicenseRequirement saved = repository.save(entity);

        eventPublisher.publishEvent(new ConstructionLicenseRequirementCreatedEvent(saved));

        Request createdRequest = requestRepository.findFirstByMunicipalServiceIdOrderByCreatedAtDesc(
                saved.getId()
        ).orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return this.toResponseWithRequestIdDTO(saved, createdRequest.getId());
    }

    @Override
    public ConstructionLicenseRequirementResponseDTO update(Long id, ConstructionLicenseRequirementUpdateRequestDTO dto) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        entity.setOwner(dto.owner());
        entity.setPhone(dto.phone());
        entity.setCep(dto.cep());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setPropertyNumber(dto.propertyNumber());
        entity.setNeighborhood(dto.neighborhood());
        entity.setConstructionAddress(dto.constructionAddress());
        entity.setReferencePoint(dto.referencePoint());
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setFloorCount(dto.floorCount());
        entity.setConstructionArea(dto.constructionArea());
        entity.setHousingUnitNumber(dto.housingUnitNumber());
        entity.setTerrainArea(dto.terrainArea());

        MunicipalService serviceToInherit = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        RequirementType type = requirementTypeRepository.findById(dto.requirementTypeId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setRequirementType(type);

        TechnicalResponsible responsible = technicalResponsibleRepository
                .findByRegistrationId(dto.technicalResponsibleRegistrationId())
                .orElseThrow(() -> new BusinessException("Responsável Técnico não encontrado com o registro: "
                        + dto.technicalResponsibleRegistrationId()));

        entity.setTechnicalResponsible(responsible);
        entity.setName(serviceToInherit.getName());
        entity.setDescription(serviceToInherit.getDescription());

        if (dto.documents() != null) {
            entity.getDocuments().clear();

            dto.documents().forEach(d -> {
                Document document = objectMapperUtil.map(d, Document.class);
                document.setRequirement(entity);
                entity.getDocuments().add(document);
            });
        }

        repository.save(entity);
        return objectMapperUtil.mapToRecord(entity, ConstructionLicenseRequirementResponseDTO.class);
    }

    @Override
    public List<ConstructionLicenseRequirementResponseDTO> findAll(final Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ConstructionLicenseRequirementDetailDTO findById(Long id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        return toDetailDTO(entity);
    }

    @Override
    public void delete(Long id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        repository.delete(entity);
    }

    @Override
    public void approveAssociation(AssociationActionRequestDTO dto) {
        Long requirementId = dto.constructionLicenseRequirementId();
        String registrationId = dto.registrationId();

        TechnicalResponsible responsible = technicalResponsibleRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new BusinessException(
                        "Technical Responsible not found with registration ID: " + registrationId));

        ConstructionLicenseRequirement entity = repository.findById(requirementId)
                .orElseThrow(() -> new BusinessException("Requirement not found"));

        if (entity.getTechnicalResponsibleStatus() != AssociationStatus.PENDING) {
            throw new BusinessException("This request has alrady been processed.");
        }
        if (entity.getTechnicalResponsible() == null
                || !entity.getTechnicalResponsible().getId().equals(responsible.getId())) {
            throw new AccessDeniedException("You are not the designated Technical Responsible for this requirement.");
        }
        entity.setTechnicalResponsibleStatus(AssociationStatus.APPROVED);
        repository.save(entity);

        List<Request> requests = entity.getRequests();
        Request request = requests.get(requests.size() - 1);
        this.monitoringService.completeCurrentMonitoringAndActivateNext(request, true);
    }

    @Override
    public ConstructionLicenseRequirementTechnicalResponsibleRejectDTO rejectAssociation(RejectionRequestDTO dto) {

        Long requirementId = dto.constructionLicenseRequirementId();
        String registrationId = dto.registrationId();

        TechnicalResponsible responsible = technicalResponsibleRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new BusinessException(
                        "Technical Responsible not found with registration ID: " + registrationId));

        ConstructionLicenseRequirement entity = repository.findById(requirementId)
                .orElseThrow(() -> new BusinessException("Requirement not found"));

        if (entity.getTechnicalResponsibleStatus() != AssociationStatus.PENDING) {
            throw new BusinessException("This request has alrady been processed.");
        }

        if (entity.getTechnicalResponsible() == null
                || !entity.getTechnicalResponsible().getId().equals(responsible.getId())) {
            throw new AccessDeniedException("You are not the designated Technical Responsible for this requirement.");
        }

        entity.setTechnicalResponsibleStatus(AssociationStatus.REJECTED);
        entity.setRejectionJustification(dto.justification());
        ConstructionLicenseRequirement saved = repository.save(entity);

        List<Request> requests = entity.getRequests();
        Request request = requests.get(requests.size() - 1);
        this.monitoringService.completeCurrentMonitoringAndActivateNext(request, false);

        return new ConstructionLicenseRequirementTechnicalResponsibleRejectDTO(
                saved.getId(),
                entity.getRejectionJustification()
        );

    }

    private ConstructionLicenseRequirementResponseDTO toResponseDTO(ConstructionLicenseRequirement entity) {
        if (entity == null) {
            return null;
        }

        String responsibleName = null;
        TechnicalResponsible responsibleEntity = entity.getTechnicalResponsible();

        if (responsibleEntity != null &&
                responsibleEntity.getUser() != null &&
                responsibleEntity.getUser().getPessoa() != null) {

            responsibleName = responsibleEntity.getUser().getPessoa().getNomeCompleto();
        }

        return new ConstructionLicenseRequirementResponseDTO(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getOwner(),
                entity.getPhone(),
                entity.getCep(),
                entity.getCpfCnpj(),
                entity.getConstructionAddress(),
                entity.getConstructionArea(),
                responsibleName,
                entity.getTechnicalResponsibleStatus(),
                entity.getStatus().toString()
        );
    }

    /**
     * Converts a {@link ConstructionLicenseRequirement} entity into a
     * {@link ConstructionLicenseRequirementWithRequestIDResponseDTO},
     * including the UUID of the associated {@link Request}.
     * <p>
     * This helper method ensures that the technical responsible's full name is
     * resolved
     * and included in the response DTO.
     *
     * @param entity    The saved ConstructionLicenseRequirement entity.
     * @param requestId The UUID of the Request associated with this requirement.
     * @return The response DTO populated with all fields.
     */
    private ConstructionLicenseRequirementWithRequestIDResponseDTO toResponseWithRequestIdDTO(
            ConstructionLicenseRequirement entity, UUID requestId) {
        if (entity == null) {
            return null;
        }

        String responsibleName = null;
        TechnicalResponsible responsibleEntity = entity.getTechnicalResponsible();

        if (responsibleEntity != null &&
                responsibleEntity.getUser() != null &&
                responsibleEntity.getUser().getPessoa() != null) {

            responsibleName = responsibleEntity.getUser().getPessoa().getNomeCompleto();
        }

        return new ConstructionLicenseRequirementWithRequestIDResponseDTO(
                entity.getId(),
                requestId,
                entity.getCreatedAt(),
                entity.getOwner(),
                entity.getPhone(),
                entity.getCep(),
                entity.getCpfCnpj(),
                entity.getConstructionAddress(),
                entity.getConstructionArea(),
                responsibleName,
                entity.getTechnicalResponsibleStatus());
    }

    /**
     * Finds a requirement and validates if it is pending review.
     * Helper method to avoid code duplication.
     */
    private ConstructionLicenseRequirement findRequirementForReview(Long requirementId) {
        ConstructionLicenseRequirement entity = repository.findById(requirementId)
                .orElseThrow(() -> new BusinessException("Solicitação não encontrada com o ID: " + requirementId));

        if (entity.getTechnicalResponsibleStatus() != AssociationStatus.PENDING) {
            throw new BusinessException("Esta solicitação já foi processada.");
        }
        return entity;
    }

    /**
     * Approves a construction license request.
     *
     * @param requirementId The ID of the request to be approved.
     * @return DTO with the data of the updated request.
     * @author Andesson Reis
     */
    public ConstructionLicenseRequirementResponseDTO acceptRequest(Long requirementId) {
        ConstructionLicenseRequirement entity = findRequirementForReview(requirementId);

        entity.setTechnicalResponsibleStatus(AssociationStatus.APPROVED);
        entity.setRejectionJustification(null);

        ConstructionLicenseRequirement updatedEntity = repository.save(entity);

        return toResponseDTO(updatedEntity);
    }

    /**
     * Rejects a construction license request.
     *
     * @param requirementId The ID of the request to be rejected.
     * @param dto           DTO containing the rejection justification.
     * @return DTO with the data of the updated request.
     * @author Andesson Reis
     */
    public ConstructionLicenseRequirementResponseDTO rejectRequest(Long requirementId, RejectionRequestDTO dto) {
        ConstructionLicenseRequirement entity = findRequirementForReview(requirementId);

        entity.setTechnicalResponsibleStatus(AssociationStatus.REJECTED);
        entity.setRejectionJustification(dto.justification());

        ConstructionLicenseRequirement updatedEntity = repository.save(entity);

        return toResponseDTO(updatedEntity);
    }

    @Override
    public List<ConstructionLicenseRequirementResponseDTO> findAllByTechnicalResponsible(UUID responsibleId) {
        List<ConstructionLicenseRequirement> requirements = repository.findByTechnicalResponsibleId(responsibleId);
        return requirements.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private ConstructionLicenseRequirementDetailDTO toDetailDTO(ConstructionLicenseRequirement entity) {
        if (entity == null) {
            return null;
        }

        TechnicalResponsible responsibleEntity = entity.getTechnicalResponsible();
        Usuario responsibleUser = responsibleEntity.getUser();

        TechnicalResponsibleResponseDTO responsibleDTO = new TechnicalResponsibleResponseDTO(
                responsibleEntity.getId(),
                responsibleEntity.getRegistrationId(),
                responsibleEntity.getResponsibleType(),
                responsibleEntity.getImagemUrl(),
                responsibleUser.getPessoa().getNomeCompleto(),
                responsibleUser.getPessoa().getCpf(),
                responsibleUser.getEmail(),
                responsibleUser.getTelefone());

        Usuario applicantUser = entity.getSolicitante();
        Pessoa applicantPerson = applicantUser.getPessoa();

        ProfilePublicDataResponseDTO applicantDTO = new ProfilePublicDataResponseDTO(
                applicantUser.getPerfilAtivo().getId(),
                applicantUser.getPerfilAtivo().getTipo(),
                applicantUser.getPerfilAtivo().getImagemUrl(),
                applicantPerson.getNomeCompleto(),
                applicantPerson.getCpf(),
                applicantUser.getTelefone(),
                applicantUser.getEmail(),
                applicantPerson.getGenero().toString(),
                applicantPerson.getDataNascimento());

        List<DocumentResponseDTO> documentDTOs = entity.getDocuments().stream()
                .map(doc -> new DocumentResponseDTO(
                        doc.getId(),
                        doc.getName(),
                        doc.getFileExtension(),
                        doc.getFileUrl()))
                .toList();
        return new ConstructionLicenseRequirementDetailDTO(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getTechnicalResponsibleStatus(),
                applicantDTO,
                responsibleDTO,
                entity.getOwner(),
                entity.getPhone(),
                entity.getCpfCnpj(),
                entity.getCep(),
                entity.getNeighborhood(),
                entity.getConstructionAddress(),
                entity.getPropertyNumber(),
                entity.getReferencePoint(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getFloorCount(),
                entity.getConstructionArea(),
                entity.getHousingUnitNumber(),
                entity.getTerrainArea(),
                documentDTOs,
                entity.getStatus().toString());
    }

    @Override
    public List<ConstructionLicenseRequirementResponseDTO> findAllByTechnicalResponsibleRegistrationId(
            String registrationId) {
        List<ConstructionLicenseRequirement> requirements = repository
                .findByTechnicalResponsibleRegistrationId(registrationId);
        return requirements.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    /**
     * Finds and retrieves a paginated list of construction license requirements
     * filtered by the specified RequirementType name.
     * It uses the repository to fetch the data and maps the entities to DTOs using
     * the existing toResponseDTO method.
     *
     * @param typeName The RequirementType name used to filter the requirements.
     * @param pageable The pagination and sorting parameters.
     * @return A Page object containing the filtered and paginated
     *         ConstructionLicenseRequirementResponseDTO list.
     * @author Caio Alves
     */
    @Override
    public Page<ConstructionLicenseRequirementResponseDTO> findByRequirementTypeName(String typeName,
            Pageable pageable) {
        Page<ConstructionLicenseRequirement> requirementPage = repository.findByRequirementTypeName(typeName, pageable);

        return requirementPage.map(this::toResponseDTO);
    }

    public ConstructionLicenseRequirementFinalizeResponseDTO rejectConstructionLicenseRequirement(
            Long constructionLicenseRequirementId, ConstructionLicenseRequirementFinalizeRequestDTO dto) {
        ConstructionLicenseRequirement license = repository.findById(constructionLicenseRequirementId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        PublicServantProfile publicServant = publicServantProfileRepository.findById(dto.publicServantId()).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (license.getTechnicalResponsibleStatus() != AssociationStatus.APPROVED) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_REQUEST_TO_FINALIZE.getMessage());
        }

        license.setStatus(RequirementStatus.REJECTED);

        Comment comment = new Comment();
        comment.setRequirement(license);
        comment.setNote(dto.comment());

        comment = commentRepository.save(comment);

        license.setComment(comment);
        repository.save(license);

        // INFO: Updates the monitoring status
        List<Request> requests = license.getRequests();
        if (requests.isEmpty()) {
            throw new BusinessException("No requests found for this municipal service.");
        }
        Request request = requests.get(requests.size() - 1);
        this.monitoringService.completeCurrentMonitoringAndActivateNext(request, false);

        return new ConstructionLicenseRequirementFinalizeResponseDTO(
                constructionLicenseRequirementId,
                publicServant.getId(),
                comment.getNote(),
                license.getStatus().toString());
    }

    public ConstructionLicenseRequirementFinalizeResponseDTO approveConstructionLicenseRequirement(
            Long constructionLicenseRequirementId, ConstructionLicenseRequirementFinalizeRequestDTO dto) {
        ConstructionLicenseRequirement license = repository.findById(constructionLicenseRequirementId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        PublicServantProfile publicServant = publicServantProfileRepository.findById(dto.publicServantId()).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (license.getTechnicalResponsibleStatus() != AssociationStatus.APPROVED) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_REQUEST_TO_FINALIZE.getMessage());
        }

        long nonApprovedCount = license.getDocuments().stream()
                .filter(document -> document.getStatus() != DocumentStatus.APPROVED).count();

        if (nonApprovedCount >= 3) {
            throw new BusinessException(BusinessExceptionMessage.FINAL_APPROVAL_CANNOT_OCCUR.getMessage());
        }

        license.setStatus(RequirementStatus.ACCEPTED);

        Comment comment = new Comment();
        comment.setRequirement(license);
        comment.setNote(dto.comment());

        comment = commentRepository.save(comment);

        license.setComment(comment);
        repository.save(license);

        // INFO: Updates the monitoring status
        List<Request> requests = license.getRequests();
        if (requests.isEmpty()) {
            throw new BusinessException("No requests found for this municipal service.");
        }
        Request request = requests.get(requests.size() - 1);
        this.monitoringService.completeCurrentMonitoringAndActivateNext(request, true);

        return new ConstructionLicenseRequirementFinalizeResponseDTO(
                constructionLicenseRequirementId,
                publicServant.getId(),
                comment.getNote(),
                license.getStatus().toString());

    }

    /**
     * Retrieves the finalized details of a Construction License Requirement
     * (Approved or Rejected),
     * including the final justification provided by the Public Servant during the
     * last review.
     *
     * <p>
     * This method is used when displaying or exporting the final outcome of a
     * construction license
     * process, containing both applicant information and administrative decision
     * metadata.
     * </p>
     *
     * <p>
     * <b>Workflow:</b>
     * </p>
     * <ol>
     * <li>Fetch the requirement entity by its unique ID.</li>
     * <li>Retrieve the most recent comment (if any) registered by the Public
     * Servant.</li>
     * <li>Map the entity to a standard detail DTO using {@code toDetailDTO()}.</li>
     * <li>Return an enriched finalized DTO, including the final justification.</li>
     * </ol>
     *
     * @param id the unique identifier of the finalized construction license
     *           requirement; must not be null
     * @return {@link ConstructionLicenseRequirementFinalizedDetailDTO} containing
     *         the full requirement data and justification
     * @throws BusinessException if the requirement is not found
     *
     * @author Andesson Reis
     */
    @Override
    @Transactional(readOnly = true)
    public ConstructionLicenseRequirementFinalizedDetailDTO findFinalizedById(@NotNull Long id) {
        // 1. Retrieve base requirement
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        // 2. Retrieve latest review note (final justification)
        Comment finalComment = commentRepository.findFirstByRequirementIdOrderByIdDesc(entity.getId()).orElse(null);
        final String finalJustification = (finalComment != null && finalComment.getNote() != null)
                ? finalComment.getNote().trim()
                : null;

        // 3. Map base entity to detail DTO
        ConstructionLicenseRequirementDetailDTO detailDTO = toDetailDTO(entity);

        // 4. Build finalized DTO (enriched with final justification)
        return new ConstructionLicenseRequirementFinalizedDetailDTO(
                detailDTO.id(),
                detailDTO.createdAt(),
                detailDTO.technicalResponsibleStatus(),
                detailDTO.solicitante(),
                detailDTO.technicalResponsible(),
                detailDTO.owner(),
                detailDTO.phone(),
                detailDTO.cpfCnpj(),
                detailDTO.cep(),
                detailDTO.neighborhood(),
                detailDTO.constructionAddress(),
                detailDTO.propertyNumber(),
                detailDTO.referencePoint(),
                detailDTO.startDate(),
                detailDTO.endDate(),
                detailDTO.floorCount(),
                detailDTO.constructionArea(),
                detailDTO.housingUnitNumber(),
                detailDTO.terrainArea(),
                detailDTO.documents(),
                detailDTO.status(),
                finalJustification);
    }

}
