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

        MunicipalService service = municipalServiceRepository.findById(dto.getMunicipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setMunicipalService(service);

        RequirementType type = requirementTypeRepository.findById(dto.getRequirementTypeId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setRequirementType(type);

        TechnicalResponsible responsible = objectMapperUtil.map(dto.getTechnicalResponsible(), TechnicalResponsible.class);
        entity.setTechnicalResponsible(responsible);

                if (dto.getDocuments() != null) {
            List<Document> docs = dto.getDocuments().stream()
                    .map(d -> {
                        Document document = objectMapperUtil.map(d, Document.class);
                        document.setRequirement(entity);
                        return document;
                    })
                    .collect(Collectors.toList());
            entity.setDocuments(docs);
        }

        repository.save(entity);
        return objectMapperUtil.map(entity, ConstructionLicenseRequirementResponseDTO.class);
    }

    @Override
    public ConstructionLicenseRequirementResponseDTO update(Integer id, ConstructionLicenseRequirementRequestDTO dto) {
    ConstructionLicenseRequirement entity = repository.findById(id)
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

    entity.setOwner(dto.getOwner());
    entity.setPhone(dto.getPhone());
    entity.setCep(dto.getCep());
    entity.setCpfCnpj(dto.getCpfCnpj());
    entity.setPropertyNumber(dto.getPropertyNumber());
    entity.setNeighborhood(dto.getNeighborhood());
    entity.setConstructionAddress(dto.getConstructionAddress());
    entity.setReferencePoint(dto.getReferencePoint());
    entity.setStartDate(dto.getStartDate());
    entity.setEndDate(dto.getEndDate());
    entity.setFloorCount(dto.getFloorCount());
    entity.setConstructionArea(dto.getConstructionArea());
    entity.setHousingUnitNumber(dto.getHousingUnitNumber());
    entity.setTerrainArea(dto.getTerrainArea());

    MunicipalService service = municipalServiceRepository.findById(dto.getMunicipalServiceId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    entity.setMunicipalService(service);

    RequirementType type = requirementTypeRepository.findById(dto.getRequirementTypeId())
            .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    entity.setRequirementType(type);

    TechnicalResponsible responsible = objectMapperUtil.map(dto.getTechnicalResponsible(), TechnicalResponsible.class);
    entity.setTechnicalResponsible(responsible);

if (dto.getDocuments() != null) {
    entity.getDocuments().clear();

    dto.getDocuments().forEach(d -> {
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
    public ConstructionLicenseRequirementResponseDTO findById(Integer id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        return objectMapperUtil.map(entity, ConstructionLicenseRequirementResponseDTO.class);
    }

    @Override
    public void delete(Integer id) {
        ConstructionLicenseRequirement entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        repository.delete(entity);
    }
}
