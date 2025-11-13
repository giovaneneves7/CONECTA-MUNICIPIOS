package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.IRoleRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.ITechnicalResponsibleRepository;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.IUserRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for managing {@link TechnicalResponsible} entities.
 * Handles creation, retrieval, and deletion of technical responsibles,
 * and converts entities to their respective DTO representations.
 *
 * @author Caio Alves
 */
@Service
@RequiredArgsConstructor
public class TechnicalResponsibleService implements ITechnicalResponsibleService {

    private final ITechnicalResponsibleRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    
    @Override
    @Transactional
    public TechnicalResponsibleResponseDTO save(TechnicalResponsibleRequestDTO dto) {

         if (repository.findByRegistrationId(dto.registrationId()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new BusinessException("User not found ." + dto.userId()));

        if(user.getStatus() != UserStatus.ACTIVE){
            throw new BusinessException("User must be ACTIVE to be assigned a Technical Responsible profile.");
        }

        TechnicalResponsible entity = new TechnicalResponsible();
        
        entity.setRegistrationId(dto.registrationId());
        entity.setResponsibleType(dto.responsibleType());

        entity.setImageUrl(dto.imageUrl());
        entity.setUser(user);
        entity.setType("TECHNICAL_RESPONSIBLE");

        Role role = roleRepository.findByName("ROLE_TECHNICAL_RESPONSIBLE")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_TECHNICAL_RESPONSIBLE");
                    newRole.setDescription("Technical Responsible Role");
                    return roleRepository.save(newRole);
                });
        entity.setRole(role);

        TechnicalResponsible savedEntity = repository.save(entity);

        return convertToDto(savedEntity);
    }

    
    @Override
    @Transactional
    public List<TechnicalResponsibleResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public Optional<TechnicalResponsibleResponseDTO> findById(UUID id) {
        return repository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<TechnicalResponsibleResponseDTO> findByRegistrationId(String registrationId){
        return repository.findByRegistrationId(registrationId)
            .map(this::convertToDto);
    }

    private TechnicalResponsibleResponseDTO convertToDto (TechnicalResponsible entity){
        User user = entity.getUser();
        String responsibleName = null;
        String email = null;
        String phone = null;
        String cpf = null;
    if (user != null && user.getPerson() != null) {
        responsibleName = user.getPerson().getFullName();
        email = user.getEmail();
        phone = user.getPhone();
        cpf = user.getPerson().getCpf();
    }
        return new TechnicalResponsibleResponseDTO(
            entity.getId(),
            entity.getRegistrationId(),
            entity.getResponsibleType(),
            entity.getImageUrl(),
            responsibleName,
            cpf,
            email,
            phone
        );
    }

}
