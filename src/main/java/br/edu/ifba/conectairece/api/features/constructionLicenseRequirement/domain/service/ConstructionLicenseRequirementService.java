package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.AssociationActionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementDetailDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.event.ConstructionLicenseRequirementCreatedEvent;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository.ConstructionLicenseRequirementRepository;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.person.domain.model.Person;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfilePublicDataResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;
import br.edu.ifba.conectairece.api.features.requirementType.domain.repository.RequirementTypeRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.TechnicalResponsibleRepository;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;

/**
 * Service responsible for managing {@link ConstructionLicenseRequirement} entities.
 * Implements business logic for handling construction license requests, 
 * including persistence and validation of relationships.
 *
 * Main features:
 * - Save new construction license requirements with:
 *   - Linked {@link MunicipalService}.
 *   - Associated {@link RequirementType}.
 *   - Assigned {@link TechnicalResponsible}.
 *   - Attached {@link Document} list.
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
public class ConstructionLicenseRequirementService implements ConstructionLicenseRequirementIService{

    private final ConstructionLicenseRequirementRepository repository;
    private final MunicipalServiceRepository municipalServiceRepository;
    private final RequirementTypeRepository requirementTypeRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final TechnicalResponsibleRepository technicalResponsibleRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository; 

    @Override
    public ConstructionLicenseRequirementResponseDTO save(ConstructionLicenseRequirementRequestDTO dto) {

        Profile solicitanteProfile = profileRepository.findById(dto.solicitanteProfileId())
            .orElseThrow(() -> new BusinessException("Perfil do solicitante não encontrado"));

        User solicitante = solicitanteProfile.getUser();
        
        ConstructionLicenseRequirement entity = objectMapperUtil.map(dto, ConstructionLicenseRequirement.class);

        entity.setSolicitante(solicitante);

        MunicipalService service = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setMunicipalService(service);

        RequirementType type = requirementTypeRepository.findById(dto.requirementTypeId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setRequirementType(type);

        TechnicalResponsible responsible = technicalResponsibleRepository.findByRegistrationId(dto.technicalResponsibleRegistrationId())
            .orElseThrow(() -> new BusinessException("Responsável Técnico não encontrado com o registro: " + dto.technicalResponsibleRegistrationId()));

        entity.setTechnicalResponsible(responsible);

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

        ConstructionLicenseRequirement saved = repository.save(entity);

        eventPublisher.publishEvent(new ConstructionLicenseRequirementCreatedEvent(saved));

        return toResponseDTO(saved);
    }

    @Override
    public ConstructionLicenseRequirementResponseDTO update(Long id, ConstructionLicenseRequirementRequestDTO dto) {
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

    MunicipalService service = municipalServiceRepository.findById(dto.municipalServiceId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    entity.setMunicipalService(service);

    RequirementType type = requirementTypeRepository.findById(dto.requirementTypeId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    entity.setRequirementType(type);

        TechnicalResponsible responsible = technicalResponsibleRepository.findByRegistrationId(dto.technicalResponsibleRegistrationId())
            .orElseThrow(() -> new BusinessException("Responsável Técnico não encontrado com o registro: " + dto.technicalResponsibleRegistrationId()));

        entity.setTechnicalResponsible(responsible);

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
    public List<ConstructionLicenseRequirementResponseDTO> findAll() {
        return repository.findAll().stream()
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
    public void approveAssociation(AssociationActionRequestDTO dto){
        Long requirementId = dto.constructionLicenseRequirementId();
        UUID responsibleId = dto.technicalResponsibleId();

        ConstructionLicenseRequirement entity = repository.findById(requirementId)
            .orElseThrow(() -> new BusinessException("Requirement not found"));

            if (entity.getTechnicalResponsibleStatus() != AssociationStatus.PENDING) {
                throw new BusinessException("This request has alrady been processed.");
            }
            if (!entity.getTechnicalResponsible().getId().equals(responsibleId)) {
                throw new AccessDeniedException("You are not authorized to approve this requirement.");
            }
            entity.setTechnicalResponsibleStatus(AssociationStatus.APPROVED);
            repository.save(entity);
    }

    @Override
    public void rejectAssociation(RejectionRequestDTO dto){

        Long requirementId = dto.constructionLicenseRequirementId();
        UUID responsibleId = dto.technicalResponsibleId();

        ConstructionLicenseRequirement entity = repository.findById(requirementId)
            .orElseThrow(() -> new BusinessException("Requirement not found"));

            if (entity.getTechnicalResponsibleStatus() != AssociationStatus.PENDING) {
                throw new BusinessException("This request has alrady been processed.");
            }

            if (!entity.getTechnicalResponsible().getId().equals(responsibleId)) {
                throw new AccessDeniedException("You are not authorized to reject this requirement.");
            }

            entity.setTechnicalResponsibleStatus(AssociationStatus.REJECTED);
            entity.setRejectionJustification(dto.justification());
            repository.save(entity);
    }

    private ConstructionLicenseRequirementResponseDTO toResponseDTO(ConstructionLicenseRequirement entity) {
        if (entity == null) {
            return null;
        }

        String responsibleName = null;
        TechnicalResponsible responsibleEntity = entity.getTechnicalResponsible();
        
        if (responsibleEntity != null && 
            responsibleEntity.getUser() != null && 
            responsibleEntity.getUser().getPerson() != null) {
        
        // Extraímos apenas o nome para uma variável String
            responsibleName = responsibleEntity.getUser().getPerson().getFullName();
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
            entity.getTechnicalResponsibleStatus()
        );
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
     * @param dto DTO containing the rejection justification.
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

    private ConstructionLicenseRequirementDetailDTO toDetailDTO(ConstructionLicenseRequirement entity){
        if(entity == null){
            return null;
        }

        TechnicalResponsible responsibleEntity = entity.getTechnicalResponsible();
        User responsibleUser = responsibleEntity.getUser();

            TechnicalResponsibleResponseDto responsibleDTO = new TechnicalResponsibleResponseDto(
            responsibleEntity.getId(),
            responsibleEntity.getRegistrationId(),
            responsibleEntity.getResponsibleType(),
            responsibleEntity.getImageUrl(),
            responsibleUser.getPerson().getFullName(),
            responsibleUser.getPerson().getCpf(),
            responsibleUser.getEmail(),
            responsibleUser.getPhone()
    );

    User applicantUser = entity.getSolicitante();
    Person applicantPerson = applicantUser.getPerson();

    ProfilePublicDataResponseDTO applicantDTO = new ProfilePublicDataResponseDTO(
        applicantUser.getActiveProfile().getId(),
        applicantUser.getActiveProfile().getType(),
        applicantUser.getActiveProfile().getImageUrl(),
        applicantPerson.getFullName(),
        applicantPerson.getCpf(),
        applicantUser.getPhone(),
        applicantUser.getEmail(),
        applicantPerson.getGender().toString(),
        applicantPerson.getBirthDate()
    );

    List<DocumentResponseDTO> documentDTOs = entity.getDocuments().stream()
            .map(doc -> new DocumentResponseDTO(
                    doc.getId(),
                    doc.getName(),
                    doc.getFileExtension(),
                    doc.getFileUrl()
            ))
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
            documentDTOs
    );
    }

    @Override
    public List<ConstructionLicenseRequirementResponseDTO> findAllByTechnicalResponsibleRegistrationId(String registrationId) {
        List<ConstructionLicenseRequirement> requirements = repository.findByTechnicalResponsibleRegistrationId(registrationId);
        return requirements.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    /**
     * Finds and retrieves a paginated list of construction license requirements filtered by the specified RequirementType name.
     * It uses the repository to fetch the data and maps the entities to DTOs using the existing toResponseDTO method.
     *
     * @param typeName The RequirementType name used to filter the requirements.
     * @param pageable The pagination and sorting parameters.
     * @return A Page object containing the filtered and paginated ConstructionLicenseRequirementResponseDTO list.
     * @author Caio Alves
     */
    @Override
    public Page<ConstructionLicenseRequirementResponseDTO> findByRequirementTypeName(String typeName, Pageable pageable) {
        Page<ConstructionLicenseRequirement> requirementPage = repository.findByRequirementTypeName(typeName, pageable);

        return requirementPage.map(this::toResponseDTO);
    }
}
