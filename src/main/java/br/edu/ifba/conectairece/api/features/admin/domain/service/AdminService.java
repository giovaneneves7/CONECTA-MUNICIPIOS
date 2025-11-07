package br.edu.ifba.conectairece.api.features.admin.domain.service;

import br.edu.ifba.conectairece.api.features.admin.domain.dto.request.AdminAssignPublicServantDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.request.AdminAssingnTechnicalResponsibleDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminProfileListResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminUserContentResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminUserDetailResponseDto;
import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import br.edu.ifba.conectairece.api.features.admin.domain.repository.AdminProfileRepository;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserDataResponseDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.person.domain.model.Person;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfilePublicDataResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.response.PublicServantRegisterResponseDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.repository.PublicServantProfileRepository;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.service.IPublicServantProfileService;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.TechnicalResponsibleRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service.ITechnicalResponsibleService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService{

    private final UserRepository userRepository;
    private final AdminProfileRepository adminProfileRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final ITechnicalResponsibleService technicalResponsibleService;
    private final IPublicServantProfileService publicServantProfileService;
    private final TechnicalResponsibleRepository technicalResponsibleRepository;
    private final PublicServantProfileRepository publicServantProfileRepository;
    private final ProfileRepository profileRepository;



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

    /**
     * Assigns a Technical Responsible profile to a specified user.
     * This action reuses the existing logic for creating a Technical Responsible profile.
     *
     * @param dto The DTO containing the user ID and the new profile's data.
     * @return A DTO with the details of the newly created Technical Responsible profile.
     * @author Caio Alves
     */
    @Override
    public TechnicalResponsibleResponseDto assignTechnicalResponsibleProfile(AdminAssingnTechnicalResponsibleDTO dto) {
        TechnicalResponsibleRequestDto requestDto = new TechnicalResponsibleRequestDto(
            dto.registrationId(),
            dto.responsibleType(),
            dto.imageUrl(),
            dto.userId()
        );
        return technicalResponsibleService.save(requestDto);
    }

    /**
     * Assigns a Public Servant profile to a specified user.
     * This action reuses the existing logic for creating a Public Servant profile.
     *
     * @param dto The DTO containing the user ID and the new profile's data.
     * @return A DTO with the details of the newly created Public Servant profile.
     * @author Caio Alves
     */   
    @Override
    public PublicServantRegisterResponseDTO assignPublicServantProfile(AdminAssignPublicServantDTO dto) {
        PublicServantProfile profile = new PublicServantProfile();
        profile.setEmployeeId(dto.employeeId());
        profile.setImageUrl(dto.imageUrl());
        profile.setType("PUBLIC_SERVANT");

        return publicServantProfileService.createPublicServantProfile(dto.userId(), profile);
    }

    /**
     * Removes the Technical Responsible profile from a user, based on the profile's ID.
     * It also handles detaching the profile from the user's profile list.
     *
     * @param profileId The ID of the Technical Responsible profile to be removed.
     * @author Caio Alves
     */
    @Override @Transactional
        public void removeTechnicalResponsibleProfile(UUID profileId) {
        TechnicalResponsible profileToRemove = technicalResponsibleRepository.findById(profileId)
            .orElseThrow(() -> new BusinessException("Perfil de Responsável Técnico não encontrado com o ID fornecido."));

        User user = profileToRemove.getUser();
        user.getProfiles().remove(profileToRemove);
        
        if (user.getActiveProfile() != null && user.getActiveProfile().getId().equals(profileToRemove.getId())) {
            user.setActiveProfile(null); 
        }
        
        userRepository.save(user);
        technicalResponsibleRepository.delete(profileToRemove);
    }

    /**
     * Removes the Public Servant profile from a user, based on the profile's ID.
     * It also handles detaching the profile from the user's profile list.
     *
     * @param profileId The ID of the Public Servant profile to be removed.
     * @author Caio Alves
     */
    @Override @Transactional
    public void removePublicServantProfile(UUID profileId) {
        PublicServantProfile profileToRemove = publicServantProfileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException("Perfil de Funcionário Público não encontrado com o ID fornecido."));

        User user = profileToRemove.getUser();

        user.getProfiles().remove(profileToRemove);
            if (user.getActiveProfile() != null && user.getActiveProfile().getId().equals(profileToRemove.getId())) {
            user.setActiveProfile(null);
        }
        
        userRepository.save(user);
        publicServantProfileRepository.delete(profileToRemove);
    }

    /**
     * Changes a user's status to ACTIVE, effectively enabling their account.
     *
     * @param userId The ID of the user to be activated.
     * @return A DTO with the updated data of the activated user.
     * @author Caio Alves
     */
    @Override @Transactional
    public UserDataResponseDTO activateUser(UUID userId){
        return updateUserStatus(userId, UserStatus.ACTIVE);
    }

    /**
     * Changes a user's status to INACTIVE, effectively disabling their account.
     *
     * @param userId The ID of the user to be deactivated.
     * @return A DTO with the updated data of the deactivated user.
     * @author Caio Alves
     */    
    @Override @Transactional
    public UserDataResponseDTO deactivateUser(UUID userId) {
        return updateUserStatus(userId, UserStatus.INACTIVE); 
    }

    /**
     * Helper method to find a user, set their status, save, and return the DTO.
     *
     * @param userId The ID of the user to update.
     * @param newStatus The new UserStatus (ACTIVE or INACTIVE).
     * @return A DTO with the updated user data.
     * @author Caio Alves
     */
    private UserDataResponseDTO updateUserStatus(UUID userId, UserStatus newStatus){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        user.setStatus(newStatus);

        User updatedUser = userRepository.save(user);

        return objectMapperUtil.map(updatedUser, UserDataResponseDTO.class);
    }

    /**
     * Retrieves detailed information about a specific user, including their core data
     * and a list of all their associated profiles.
     *
     * @param userId The UUID of the user to retrieve details for.
     * @return An AdminUserDetailResponseDTO containing the user's details.
     * @throws BusinessException if the user is not found.
     * @author Caio Alves
     */
    @Override @Transactional
    public Page<AdminUserDetailResponseDto> findAllUserDetails(Pageable pageable) {

        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(this::mapUserToAdminDetailDto);
    }

        /**
     * Retrieves a paginated list of detailed information for users filtered by a specific role name
     * associated with any of their profiles.
     *
     * @param roleName The name of the Role to filter users by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing AdminUserDetailResponseDTO objects for the filtered users.
     * @author Caio Alves
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminUserDetailResponseDto> findUserDetailsByRoleName(String roleName, Pageable pageable) {

        Page<User> userPage = userRepository.findByProfileRoleName(roleName, pageable);

        return userPage.map(this::mapUserToAdminDetailDto);
    }

    /**
     * Retrieves a paginated list of detailed information for users filtered by a specific status.
     *
     * @param status The UserStatus enum to filter users by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing AdminUserDetailResponseDTO objects for the filtered users.
     * @author Caio Alves
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminUserDetailResponseDto> findUserDetailsByStatus(UserStatus status, Pageable pageable) {
        Page<User> userPage = userRepository.findByStatus(status, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }

    /**
     * Searches for users (with admin details) by name or CPF.
     *
     * @param term The search term (name or CPF).
     * @param pageable Pagination information.
     * @return A Page of DTOs with the details of the found users.
     * @author Caio Alves
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminUserDetailResponseDto> findUserDetailsByNameOrCpf(String term, Pageable pageable) {
        Page<User> userPage = userRepository.findByFullNameOrCpfContaining(term, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }
    
    /**
     * Maps a User entity to the detailed AdminUserDetailResponseDto.
     * @param user The User entity to map.
     * @return The corresponding AdminUserDetailResponseDto.
     * @Author Caio Alves
     */
    private AdminUserDetailResponseDto mapUserToAdminDetailDto(User user) {
        Person person = user.getPerson();
        Profile activeProfile = user.getActiveProfile();

        AdminUserContentResponseDTO contentDto = new AdminUserContentResponseDTO(
                user.getId(),
                activeProfile != null ? activeProfile.getType() : null,
                activeProfile != null ? activeProfile.getImageUrl() : null,
                person != null ? person.getFullName() : null,
                person != null ? person.getCpf() : null,
                user.getPhone(),
                user.getEmail(),
                person != null && person.getGender() != null ? person.getGender().toString() : null,
                person != null ? person.getBirthDate() : null,
                user.getStatus() != null ? user.getStatus().toString() : null
        );

        List<AdminProfileListResponseDTO> profileListDto = user.getProfiles().stream()
                .map(profile -> {
                    
                    String displayType; 
                    String specificType = null;

                    if (profile instanceof TechnicalResponsible) {
                        TechnicalResponsible tr = (TechnicalResponsible) profile;
                        displayType = "Técnico Responsável"; 
                        specificType = tr.getResponsibleType(); 

                    } else if (profile instanceof PublicServantProfile) {
                        PublicServantProfile ps = (PublicServantProfile) profile;
                        displayType = "Servidor Público";
                        specificType = ps.getType(); 
                    
                    } else if (profile instanceof AdminProfile) {
                        AdminProfile ap = (AdminProfile) profile;
                        displayType = "Administrador";
                        specificType = ap.getType();
                        
                    
                    } else {
                        displayType = profile.getType(); 
                    }
                    
                    return new AdminProfileListResponseDTO(
                            profile.getId(),
                            displayType, 
                            profile.getImageUrl(),
                            specificType  
                    );
                })
                .collect(Collectors.toList());

        return new AdminUserDetailResponseDto(contentDto, profileListDto);
    }
}
