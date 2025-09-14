package br.edu.ifba.conectairece.api.features.profile.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.request.ProfileRequestDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.projection.ProfileProjection;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class responsible for handling business logic related to {@link Profile}.
 *
 * @author Jorge Roberto, Giovane Neves
 */
@RequiredArgsConstructor
@Service
public class ProfileService implements ProfileIService {

    private final ProfileRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final RoleRepository roleRepository;
    private final RequestRepository requestRepository;
    private  final UserRepository userRepository;

    @Override @Transactional
    public ProfileResponseDTO save(ProfileRequestDTO dto) {
        try {
            Profile profile = new Profile();
            profile.setType(dto.type());
            profile.setImageUrl(dto.imageUrl());

            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new BusinessException("User not found"));
            profile.setUser(user);

            Role role = new Role();
            role.setName("ROLE_USER");
            role.setDescription("Tests");
            role = roleRepository.save(role);
            profile.setRole(role);

            Profile saved = repository.save(profile);
            return objectMapperUtil.map(saved, ProfileResponseDTO.class);

        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    @Override @Transactional
    public ProfileResponseDTO update(Profile profile) {
        Profile existing = this.findById(profile.getId());

        existing.setType(profile.getType());
        existing.setImageUrl(profile.getImageUrl());

        return objectMapperUtil.map(repository.save(existing),  ProfileResponseDTO.class);
    }

    @Override @Transactional
    public void delete(Long id) {
        Profile profile = this.findById(id);
        if (profile.getUser() != null) {
            throw new BusinessException(BusinessExceptionMessage.CLASS_IN_USE.getMessage());
        }
        repository.delete(profile);
    }

    @Override @Transactional(readOnly = true)
    public Profile findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    @Override @Transactional(readOnly = true)
    public Page<ProfileProjection> findAllProjectedBy(Pageable pageable) {
        return repository.findAllProjectedBy(pageable);
    }

    /**
     * Find all requests linked to the user id passed as a parameter
     *
     * @author Giovane Neves
     * @param userId The userId linked to the requests
     * @return A pageable list of requests linked to the user id passed as a parameter
     */
    @Override
    public Page<Request> findAllRequestsByProfileId(UUID userId, Pageable pageable) {

        return this.requestRepository.findAllByProfileId(userId, pageable);

    }
}
