package br.edu.ifba.conectairece.api.features.admin.domain.service;

import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import br.edu.ifba.conectairece.api.features.admin.domain.repository.AdminProfileRepository;
import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService{

    private final UserRepository userRepository;
    private final AdminProfileRepository adminProfileRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override @Transactional
    public AdminResponseDTO createAdmin(UUID userId, AdminProfile admin) {
        if (admin == null || userId == null) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_DATA.getMessage());
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())
        );

        boolean alreadyHasAdminProfile = user.getProfiles().stream().anyMatch(p -> p instanceof AdminProfile);

        if (alreadyHasAdminProfile) {
            throw new BusinessException(BusinessExceptionMessage.USER_ALREADY_HAS_THIS_PROFILE.getMessage());
        }

        Role role = new Role();
        role.setName("ADMIN");
        role.setDescription("Role for administrator");
        roleRepository.save(role);

        admin.setRole(role);
        admin.setUser(user);

        admin = adminProfileRepository.save(admin);

        user.getProfiles().add(admin);

        if (user.getActiveProfile() == null) {
            user.setActiveProfile(admin);
        }

        userRepository.save(user);

        return objectMapperUtil.mapToRecord(admin, AdminResponseDTO.class);
    }

    @Override @Transactional
    public void update(UUID userId, AdminProfile profile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        AdminProfile existing = user.getProfiles().stream()
                        .filter(p -> p instanceof AdminProfile)
                        .map(p -> (AdminProfile) p)
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_PROFILE.getMessage()));

        existing.setType(profile.getType());
        existing.setImageUrl(profile.getImageUrl());

        adminProfileRepository.save(existing);
    }

    @Override @Transactional
    public void delete(UUID id) {
        AdminProfile profile = this.adminProfileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        adminProfileRepository.delete(profile);
    }

    @Override @Transactional(readOnly = true)
    public AdminResponseDTO findById(UUID id) {
        AdminProfile found = adminProfileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return objectMapperUtil.mapToRecord(found, AdminResponseDTO.class);
    }

    @Override @Transactional(readOnly = true)
    public List<AdminResponseDTO> findAll(Pageable pageable) {
        Page<AdminProfile> profiles = adminProfileRepository.findAll(pageable);
        return profiles.stream()
                .map(profile -> new AdminResponseDTO(
                        profile.getId(),
                        profile.getType(),
                        profile.getImageUrl()
                ))
                .toList();
    }
}
