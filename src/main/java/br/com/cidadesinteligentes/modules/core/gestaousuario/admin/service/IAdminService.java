package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request.AdminAssignPublicServantDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request.AdminAssingnTechnicalResponsibleDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response.AdminResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response.AdminUserDetailResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.model.AdminProfile;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response.PublicServantRegisterResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response.TechnicalResponsibleResponseDTO;

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
    TechnicalResponsibleResponseDTO assignTechnicalResponsibleProfile(AdminAssingnTechnicalResponsibleDTO dto);

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
     * Changes a user's status to ACTIVE, effectively enabling their account.
     *
     * @param userId The ID of the user to be activated.
     * @return A DTO with the updated data of the activated user.
     * @author Caio Alves 
     */
    UserDataResponseDTO activateUser(UUID userId);

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
    Page<AdminUserDetailResponseDTO> findAllUserDetails(Pageable pageable);

    /**
     * Retrieves a paginated list of detailed information for users filtered by a specific role name
     * associated with any of their profiles.
     *
     * @param roleName The name of the Role to filter users by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing AdminUserDetailResponseDTO objects for the filtered users.
     * @author Caio Alves
     */
    Page<AdminUserDetailResponseDTO> findUserDetailsByRoleName(String roleName, Pageable pageable);

    /**
     * Retrieves a paginated list of detailed information for users filtered by a specific status.
     *
     * @param status The UserStatus enum to filter users by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing AdminUserDetailResponseDTO objects for the filtered users.
     * @author Caio Alves
     */
    Page<AdminUserDetailResponseDTO> findUserDetailsByStatus(StatusUsuario status, Pageable pageable);

   /**
     * Searches for users (with admin details) by name or CPF.
     *
     * @param term The search term (name or CPF).
     * @param pageable Pagination information.
     * @return A Page of DTOs with the details of the found users.
     * @author Caio Alves
     */ 
    Page<AdminUserDetailResponseDTO> findUserDetailsByNameOrCpf(String term, Pageable pageable);

    /**
     * Searches for users (with admin details) by both role name and user status.
     *
     * @param roleName The name of the Role to filter by.
     * @param status The UserStatus to filter by.
     * @param pageable Pagination information.
     * @return A Page of DTOs with the details of the found users.
     * @author Caio Alves 
     */
    Page<AdminUserDetailResponseDTO> findUserDetailsByRoleNameAndStatus(String roleName, StatusUsuario status, Pageable pageable);
}
