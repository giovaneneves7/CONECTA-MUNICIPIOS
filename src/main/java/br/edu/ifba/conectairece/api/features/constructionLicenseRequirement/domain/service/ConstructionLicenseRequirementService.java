package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.event.ConstructionLicenseRequirementCreatedEvent;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository.ConstructionLicenseRequirementRepository;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;
import br.edu.ifba.conectairece.api.features.requirementType.domain.repository.RequirementTypeRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.TechnicalResponsibleRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;

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
 * Author: Caio Alves
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

    @Override
    public ConstructionLicenseRequirementResponseDTO save(ConstructionLicenseRequirementRequestDTO dto) {

        ConstructionLicenseRequirement entity = objectMapperUtil.map(dto, ConstructionLicenseRequirement.class);

        MunicipalService service = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setMunicipalService(service);

        RequirementType type = requirementTypeRepository.findById(dto.requirementTypeId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setRequirementType(type);

        TechnicalResponsible responsible = technicalResponsibleRepository.findById(dto.technicalResponsibleId())
            .orElseThrow(() -> new BusinessException("Technical Responsible not found"));
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

        return objectMapperUtil.mapToRecord(saved, ConstructionLicenseRequirementResponseDTO.class);
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

        TechnicalResponsible responsible = technicalResponsibleRepository.findById(dto.technicalResponsibleId())
            .orElseThrow(() -> new BusinessException("Technical Responsible not found"));
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
    public ConstructionLicenseRequirementResponseDTO findById(Long id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        return toResponseDTO(entity);
    }

    @Override
    public void delete(Long id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        repository.delete(entity);
    }

    @Override
    public void approveAssociation(Long requirementId, UUID responsibleId){
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
    public void rejectAssociation(Long requirementId, UUID responsibleId, RejectionRequestDTO dto){
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
        
        return new ConstructionLicenseRequirementResponseDTO(
            entity.getId(),
            entity.getCreatedAt(), 
            entity.getOwner(),
            entity.getPhone(),
            entity.getCep(),
            entity.getCpfCnpj(),
            entity.getConstructionAddress(),
            entity.getConstructionArea(),
            entity.getTechnicalResponsibleStatus()
        );
    }
}
