package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.response.PublicServantRegisterResponseDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.repository.PublicServantProfileRepository;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PublicServantProfileService implements IPublicServantProfileService{

    private final UserRepository userRepository;
    private final PublicServantProfileRepository publicServantRepository;
    private final RoleRepository roleRepository;

    @Override @Transactional
    public PublicServantRegisterResponseDTO createPublicServantProfile(UUID userId, PublicServantProfile employee) {
        if (employee == null || userId == null) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_DATA.getMessage());
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())
        );

        boolean alreadyHasPublicServantProfile = user.getProfiles().stream().anyMatch(p -> p instanceof PublicServantProfile);

        if (alreadyHasPublicServantProfile) {
            throw new BusinessException(BusinessExceptionMessage.USER_ALREADY_HAS_THIS_PROFILE.getMessage());
        }

        Role role = new Role();
        role.setName("ROLE_PUBLIC_SERVANT");
        role.setDescription("Role for public servant");
        roleRepository.save(role);

        employee.setRole(role);
        employee.setUser(user);

        employee = publicServantRepository.save(employee);

        user.getProfiles().add(employee);

        if (user.getActiveProfile() == null) {
            user.setActiveProfile(employee);
        }

        userRepository.save(user);

        return new PublicServantRegisterResponseDTO(employee.getEmployeeId());
    }
}
