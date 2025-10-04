package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.TechnicalResponsibleRepository;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.UserRepository;
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

    private final TechnicalResponsibleRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TechnicalResponsibleResponseDto save(UUID userId, TechnicalResponsibleRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com o ID: " + userId));

         if (repository.findByRegistrationId(dto.registrationId()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        TechnicalResponsible entity = objectMapperUtil.map(dto, TechnicalResponsible.class);
        
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

        return objectMapperUtil.mapToRecord(savedEntity, TechnicalResponsibleResponseDto.class);
    }

    
 @Override
    @Transactional
    public List<TechnicalResponsibleResponseDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> objectMapperUtil.mapToRecord(entity, TechnicalResponsibleResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public Optional<TechnicalResponsibleResponseDto> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id)
                .map(entity -> objectMapperUtil.mapToRecord(entity, TechnicalResponsibleResponseDto.class))
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        TechnicalResponsible entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        repository.delete(entity);
    }

}
