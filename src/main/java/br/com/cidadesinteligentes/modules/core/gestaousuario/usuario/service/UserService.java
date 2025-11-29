package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.UserStatus;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileWithRoleResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IProfileRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.User;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUserRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;

import java.util.List;
import java.util.UUID;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IProfileRepository profileRepository;
    private final IUserRepository userRepository;

    /**
     * Searches for a user by the ID passed as a parameter
     *
     * @author Giovane Neves
     *
     * @param id The id of the user to be found
     * @return DTO with the found user data
     */
    @Transactional(readOnly = true)
    public UserDataResponseDTO getUserById(final UUID id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        return objectMapperUtil.map(user, UserDataResponseDTO.class);

    }

    @Transactional(readOnly = true)
    public List<ProfileWithRoleResponseDTO> getUserProfiles(final UUID id, final Pageable pageable) {

        Page<Profile> profiles = this.profileRepository.findAllByUserId(id, pageable);

        return profiles.stream()
                .map(profile -> new ProfileWithRoleResponseDTO(
                        profile.getId(),
                        profile.getRole().getName(),
                        profile.getType(),
                        profile.getImageUrl()
                ))
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public ProfileWithRoleResponseDTO findActiveProfileByUserId(final UUID id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (user.getActiveProfile() == null) {
            throw new BusinessException(BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage());
        }

        Profile profile = user.getActiveProfile();

        return new ProfileWithRoleResponseDTO(
                profile.getId(),
                profile.getRole().getName(),
                profile.getType(),
                profile.getImageUrl()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers(final Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);
        return users.stream()
                .map(user -> this.objectMapperUtil.mapToRecord(user, UserResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public void updateUserStatus(UUID userId, UserStatus newStatus) {
            User user = this.findById(userId);
            user.setStatus(newStatus);
            this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }
}
