package br.edu.ifba.conectairece.api.features.profile.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.projection.ProfileProjection;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class responsible for handling business logic related to {@link Profile}.
 *
 * @author Jorge Roberto
 */
@RequiredArgsConstructor
@Service
public class ProfileService implements ProfileIService {
    private final ProfileRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final RoleRepository roleRepository;


    @Override @Transactional
    public ProfileResponseDTO save(Profile profile) {
        try {
            //TODO: ADD ROLE BUSINESS RULE
            Role role = new Role();
            role.setName("ROLE_USER");
            role.setDescription("Tests");
            role = roleRepository.save(role);
            profile.setRole(role);

            return objectMapperUtil.map(repository.save(profile),  ProfileResponseDTO.class);
        }  catch (Exception ex) {
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
        if (!profile.getUsers().isEmpty()) {
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
}
