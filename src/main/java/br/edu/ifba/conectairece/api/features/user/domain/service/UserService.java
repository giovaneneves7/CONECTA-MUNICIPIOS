package br.edu.ifba.conectairece.api.features.user.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserDataResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;

import java.util.List;
import java.util.UUID;

import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final ObjectMapperUtil objectMapperUtil;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    /**
     * Searches for a user by the ID passed as a parameter
     *
     * @author Giovane Neves
     *
     * @param id The id of the user to be found
     * @return DTO with the found user data
     */
    public UserDataResponseDTO getUserById(final UUID id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        return objectMapperUtil.map(user, UserDataResponseDTO.class);

    }

    public List<ProfileResponseDTO> getUserProfiles(final UUID id){

        List<Profile> profiles = this.profileRepository.findAllByUserId(id);

        return profiles.stream()
                .map(profile -> objectMapperUtil.mapToRecord(profile, ProfileResponseDTO.class))
                .toList();
    }


    @Override @Transactional(readOnly = true)
    public ProfileResponseDTO findActiveProfileByUserId(UUID id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (user.getActiveProfile() == null) {
            throw new BusinessException(BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage());
        }

        Profile profile = user.getActiveProfile();

        return new ProfileResponseDTO(
                profile.getId(),
                profile.getType(),
                profile.getImageUrl()
        );
    }
}
