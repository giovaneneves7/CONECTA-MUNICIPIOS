package br.edu.ifba.conectairece.api.features.admin.domain.service;

import br.edu.ifba.conectairece.api.features.admin.domain.dto.request.AdminAssignPublicServantDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.request.AdminAssingnTechnicalResponsibleDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminUserDetailResponseDto;
import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserDataResponseDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.response.PublicServantRegisterResponseDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAdminService {
    AdminResponseDTO createAdmin(UUID userId, AdminProfile admin);
    void update(UUID userId, AdminProfile admin);
    void delete(UUID id);
    AdminResponseDTO findById(UUID id);
    List<AdminResponseDTO> findAll(Pageable pageable);

    /**
     * Assigns a Technical Responsible profile to a specified user.
     * This action reuses the existing logic for creating a Technical Responsible profile.
     *
     * @param dto The DTO containing the user ID and the new profile's data.
     * @return A DTO with the details of the newly created Technical Responsible profile.
     * @author Caio Alves
     */
    TechnicalResponsibleResponseDto assignTechnicalResponsibleProfile(AdminAssingnTechnicalResponsibleDTO dto);

    /**
     * Assigns a Public Servant profile to a specified user.
     * This action reuses the existing logic for creating a Public Servant profile.
     *
     * @param dto The DTO containing the user ID and the new profile's data.
     * @return A DTO with the details of the newly created Public Servant profile.
     * @author Caio Alves
     */
    PublicServantRegisterResponseDTO assignPublicServantProfile(AdminAssignPublicServantDTO dto);

    /**
     * Removes the Technical Responsible profile from a user, based on the profile's ID.
     * It also handles detaching the profile from the user's profile list.
     *
     * @param profileId The ID of the Technical Responsible profile to be removed.
     * @author Caio Alves
     */
    void removeTechnicalResponsibleProfile(UUID profileId);
    
    /**
     * Removes the Public Servant profile from a user, based on the profile's ID.
     * It also handles detaching the profile from the user's profile list.
     *
     * @param profileId The ID of the Public Servant profile to be removed.
     * @author Caio Alves
     */
    void removePublicServantProfile(UUID profileId);

    /**
     * Changes a user's status to INACTIVE, effectively disabling their account.
     *
     * @param userId The ID of the user to be deactivated.
     * @return A DTO with the updated data of the deactivated user.
     * @author Caio Alves
     */
    UserDataResponseDTO deactivateUser(UUID userId);

/**
     * Retrieves a paginated list of detailed information for all users.
     * Includes core user data and a list of associated profiles for each user.
     *
     * @param pageable Pagination and sorting information.
     * @return A Page containing AdminUserDetailResponseDTO objects for all users.
     * @author Caio Alves
     */
    Page<AdminUserDetailResponseDto> findAllUserDetails(Pageable pageable);
}
