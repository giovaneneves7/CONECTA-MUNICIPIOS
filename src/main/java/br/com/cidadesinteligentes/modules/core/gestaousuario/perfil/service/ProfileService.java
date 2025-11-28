package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Role;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissionResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileWithRoleResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.User;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.IRoleRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUserRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfilePublicDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileResponseCurrentTypeDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IProfileRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.IRequestRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to {@link Profile}.
 *
 * @author Jorge Roberto, Giovane Neves
 */
@RequiredArgsConstructor
@Service
public class ProfileService implements IProfileService {

    private final IProfileRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IRoleRepository roleRepository;
    private final IRequestRepository requestRepository;
    private  final IUserRepository userRepository;

    @Override @Transactional
    public ProfileWithRoleResponseDTO update(Profile profile) {
        Profile existing = this.repository.findById(profile.getId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        existing.setType(profile.getType());
        existing.setImageUrl(profile.getImageUrl());

        existing = repository.save(existing);

        return new ProfileWithRoleResponseDTO(
                existing.getId(),
                existing.getRole().getName(),
                existing.getType(),
                existing.getImageUrl()
        );
    }

    @Override @Transactional
    public void delete(UUID id) {
        Profile profile = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (profile.getUser() != null) {
            throw new BusinessException(BusinessExceptionMessage.CLASS_IN_USE.getMessage());
        }
        repository.delete(profile);
    }

    @Override @Transactional(readOnly = true)
    public ProfilePublicDataResponseDTO findById(UUID id) {

        Profile found = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return new ProfilePublicDataResponseDTO(
                found.getId(),
                found.getType(),
                found.getImageUrl(),
                found.getUser().getUsername(),
                found.getUser().getPerson().getCpf(),
                found.getUser().getPhone(),
                found.getUser().getEmail(),
                found.getUser().getPerson().getGender(),
                found.getUser().getPerson().getBirthDate()
                );

    }

    @Override @Transactional(readOnly = true)
    public List<ProfileWithRoleResponseDTO> getAllProfiles(Pageable pageable) {

        Page<Profile> profiles = repository.findAll(pageable);
        return profiles.stream()
                .map(profile -> new ProfileWithRoleResponseDTO(
                        profile.getId(),
                        profile.getRole().getName(),
                        profile.getType(),
                        profile.getImageUrl()
                ))
                .toList();

    }

    /**
     * Find all requests linked to the user id passed as a parameter
     *
     * @author Giovane Neves
     * @param userId The userId linked to the requests
     * @return A pageable list of requests linked to the user id passed as a parameter
     */
    @Override
    public Page<RequestResponseDTO> findAllRequestsByProfileId(UUID userId, Pageable pageable) {

        return this.requestRepository.findAllByProfileId(userId, pageable)
                .map(request -> this.objectMapperUtil.mapToRecord(request, RequestResponseDTO.class));

    }

    @Override @Transactional
    public ProfileResponseCurrentTypeDTO changeActiveProfile(UUID userId, String newActiveType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Profile newActiveProfile = user.getProfiles().stream()
                .filter(p -> p.getType().equals(newActiveType))
                .findFirst()
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_PROFILE.getMessage()));

        user.setActiveProfile(newActiveProfile);
        userRepository.save(user);

        return new ProfileResponseCurrentTypeDTO(newActiveType, newActiveProfile.getRole().getName());
    }

    @Override @Transactional(readOnly = true)
    public List<PermissionResponseDTO> findAllPermissionsByProfile(UUID profileId, Pageable pageable) {
        Profile profile = repository.findById(profileId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Role role = profile.getRole();

        if (role == null || role.getPermissions() == null || role.getPermissions().isEmpty())  {
            return Collections.emptyList();
        }

        return role.getPermissions().stream()
                .map(permission -> this.objectMapperUtil.mapToRecord(permission, PermissionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
