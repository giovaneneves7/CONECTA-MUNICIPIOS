package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository.ConstructionLicenseRequirementRepository;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;
import br.edu.ifba.conectairece.api.features.requirementType.domain.repository.RequirementTypeRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
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

    @Override
    public ConstructionLicenseRequirementResponseDTO save(ConstructionLicenseRequirementRequestDTO dto) {
        ConstructionLicenseRequirement entity = objectMapperUtil.map(dto, ConstructionLicenseRequirement.class);

        MunicipalService service = municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setMunicipalService(service);

        RequirementType type = requirementTypeRepository.findById(dto.requirementTypeId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setRequirementType(type);

        TechnicalResponsible responsible = objectMapperUtil.map(dto.technicalResponsible(), TechnicalResponsible.class);
        entity.setTechnicalResponsible(responsible);

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

        repository.save(entity);
        return objectMapperUtil.mapToRecord(entity, ConstructionLicenseRequirementResponseDTO.class);
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

    TechnicalResponsible responsible = objectMapperUtil.map(dto.technicalResponsible(), TechnicalResponsible.class);
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
        return objectMapperUtil.map(entity, ConstructionLicenseRequirementResponseDTO.class);
    }

    @Override
    public List<ConstructionLicenseRequirementResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), ConstructionLicenseRequirementResponseDTO.class);
    }

    @Override
    public ConstructionLicenseRequirementResponseDTO findById(Long id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        return objectMapperUtil.map(entity, ConstructionLicenseRequirementResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        repository.delete(entity);
    }
}
