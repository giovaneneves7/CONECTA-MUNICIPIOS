package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.request.TechnicalResponsibleRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response.TechnicalResponsibleResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model.TechnicalResponsible;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.repository.ITechnicalResponsibleRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
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
    private final ICargoRepository roleRepository;
    private final IUsuarioRepository userRepository;

    
    @Override
    @Transactional
    public TechnicalResponsibleResponseDTO save(final TechnicalResponsibleRequestDTO dto) {

         if (repository.findByRegistrationId(dto.registrationId()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        Usuario user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new BusinessException("User not found ." + dto.userId()));

        if(user.getStatus() != StatusUsuario.ATIVO){
            throw new BusinessException("User must be ACTIVE to be assigned a Technical Responsible profile.");
        }

        TechnicalResponsible entity = new TechnicalResponsible();
        
        entity.setRegistrationId(dto.registrationId());
        entity.setResponsibleType(dto.responsibleType());

        entity.setImagemUrl(dto.imageUrl());
        entity.setUsuario(user);
        entity.setTipo("TECHNICAL_RESPONSIBLE");

        Cargo role = roleRepository.findByNome("ROLE_TECHNICAL_RESPONSIBLE")
                .orElseGet(() -> {
                    Cargo newRole = new Cargo();
                    newRole.setNome("ROLE_TECHNICAL_RESPONSIBLE");
                    newRole.setDescricao("Technical Responsible Role");
                    return roleRepository.save(newRole);
                });
        entity.setCargo(role);

        TechnicalResponsible savedEntity = repository.save(entity);

        return convertToDto(savedEntity);
    }

    
    @Override
    @Transactional
    public List<TechnicalResponsibleResponseDTO> findAll(final Pageable pageable) {

        return repository.findAll(pageable).stream()
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
    public void delete(final UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<TechnicalResponsibleResponseDTO> findByRegistrationId(final String registrationId){
        return repository.findByRegistrationId(registrationId)
            .map(this::convertToDto);
    }

    private TechnicalResponsibleResponseDTO convertToDto (final TechnicalResponsible entity){
        Usuario user = entity.getUsuario();
        String responsibleName = null;
        String email = null;
        String phone = null;
        String cpf = null;
    if (user != null && user.getPessoa() != null) {
        responsibleName = user.getPessoa().getNomeCompleto();
        email = user.getEmail();
        phone = user.getTelefone();
        cpf = user.getPessoa().getCpf();
    }
        return new TechnicalResponsibleResponseDTO(
            entity.getId(),
            entity.getRegistrationId(),
            entity.getResponsibleType(),
            entity.getImagemUrl(),
            responsibleName,
            cpf,
            email,
            phone
        );
    }

}
