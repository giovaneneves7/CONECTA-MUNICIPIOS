package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request.AdminAssignPublicServantDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request.AdminAssingnTechnicalResponsibleDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response.AdminProfileListResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response.AdminResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response.AdminUserContentResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response.AdminUserDetailResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.model.AdminProfile;
import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.repository.IAdminProfileRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response.PublicServantRegisterResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.repository.IPublicServantProfileRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.service.IPublicServantProfileService;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.request.TechnicalResponsibleRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response.TechnicalResponsibleResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model.TechnicalResponsible;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.repository.ITechnicalResponsibleRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.service.ITechnicalResponsibleService;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
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

    private final IUsuarioRepository userRepository;
    private final IAdminProfileRepository adminProfileRepository;
    private final ICargoRepository roleRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final ITechnicalResponsibleService technicalResponsibleService;
    private final IPublicServantProfileService publicServantProfileService;
    private final ITechnicalResponsibleRepository technicalResponsibleRepository;
    private final IPublicServantProfileRepository publicServantProfileRepository;
    private final IPerfilRepository profileRepository;



    @Override @Transactional
    public AdminResponseDTO createAdmin(UUID userId, AdminProfile admin) {
        if (admin == null || userId == null) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_DATA.getMessage());
        }

        Usuario user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())
        );

        if (user.getStatus() != StatusUsuario.ATIVO) {
            throw new BusinessException("User must be ACTIVE to be assigned an Admin profile.");
        }

        boolean alreadyHasAdminProfile = user.getPerfis().stream().anyMatch(p -> p instanceof AdminProfile);

        if (alreadyHasAdminProfile) {
            throw new BusinessException(BusinessExceptionMessage.USER_ALREADY_HAS_THIS_PROFILE.getMessage());
        }

        Cargo role = new Cargo();
        role.setNome("ROLE_ADMIN");
        role.setDescricao("Role for administrator");
        roleRepository.save(role);

        admin.setCargo(role);
        admin.setUsuario(user);

        admin = adminProfileRepository.save(admin);

        user.getPerfis().add(admin);

        if (user.getTipoAtivo() == null) {
            user.setTipoAtivo(admin);
        }

        userRepository.save(user);

        return objectMapperUtil.mapToRecord(admin, AdminResponseDTO.class);
    }

    @Override @Transactional
    public void update(UUID userId, AdminProfile profile) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        AdminProfile existing = user.getPerfis().stream()
                        .filter(p -> p instanceof AdminProfile)
                        .map(p -> (AdminProfile) p)
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_PROFILE.getMessage()));

        existing.setTipo(profile.getTipo());
        existing.setImagemUrl(profile.getImagemUrl());

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
                        profile.getTipo(),
                        profile.getImagemUrl()
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
    public TechnicalResponsibleResponseDTO assignTechnicalResponsibleProfile(AdminAssingnTechnicalResponsibleDTO dto) {
        TechnicalResponsibleRequestDTO requestDto = new TechnicalResponsibleRequestDTO(
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
        profile.setImagemUrl(dto.imageUrl());
        profile.setTipo(dto.type());

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

        Usuario user = profileToRemove.getUsuario();
        user.getPerfis().remove(profileToRemove);
        
        if (user.getTipoAtivo() != null && user.getTipoAtivo().getId().equals(profileToRemove.getId())) {
            user.setTipoAtivo(null);
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

        Usuario user = profileToRemove.getUsuario();

        user.getPerfis().remove(profileToRemove);
            if (user.getTipoAtivo() != null && user.getTipoAtivo().getId().equals(profileToRemove.getId())) {
            user.setTipoAtivo(null);
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
    public UsuarioResponseDTO activateUser(UUID userId){
        return updateUserStatus(userId, StatusUsuario.ATIVO);
    }

    /**
     * Changes a user's status to INACTIVE, effectively disabling their account.
     *
     * @param userId The ID of the user to be deactivated.
     * @return A DTO with the updated data of the deactivated user.
     * @author Caio Alves
     */    
    @Override @Transactional
    public UsuarioResponseDTO deactivateUser(UUID userId) {
        return updateUserStatus(userId, StatusUsuario.INATIVO); 
    }

    /**
     * Helper method to find a user, set their status, save, and return the DTO.
     *
     * @param userId The ID of the user to update.
     * @param newStatus The new UserStatus (ACTIVE or INACTIVE).
     * @return A DTO with the updated user data.
     * @author Caio Alves
     */
    private UsuarioResponseDTO updateUserStatus(UUID userId, StatusUsuario newStatus){

        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        user.setStatus(newStatus);

        Usuario updatedUser = userRepository.save(user);

        return objectMapperUtil.map(updatedUser, UsuarioResponseDTO.class);
    }

    /**
     * Retrieves detailed information about a specific user, including their core data
     * and a list of all their associated profiles.
     *
     * @return An AdminUserDetailResponseDTO containing the user's details.
     * @throws BusinessException if the user is not found.
     * @author Caio Alves
     */
    @Override @Transactional
    public Page<AdminUserDetailResponseDTO> findAllUserDetails(Pageable pageable) {

        Page<Usuario> userPage = userRepository.findAll(pageable);

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
    public Page<AdminUserDetailResponseDTO> findUserDetailsByRoleName(String roleName, Pageable pageable) {

        Page<Usuario> userPage = userRepository.findByPerfisCargoNome(roleName, pageable);

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
    public Page<AdminUserDetailResponseDTO> findUserDetailsByStatus(StatusUsuario status, Pageable pageable) {
        Page<Usuario> userPage = userRepository.findByStatus(status, pageable);
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
    public Page<AdminUserDetailResponseDTO> findUserDetailsByNameOrCpf(String term, Pageable pageable) {
        Page<Usuario> userPage = userRepository.findByNomeCompletoOrCpfContaining(term, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }

    /**
     * Searches for users (with admin details) by both role name and user status.
     *
     * @param roleName The name of the Role to filter by.
     * @param status The UserStatus to filter by.
     * @param pageable Pagination information.
     * @return A Page of DTOs with the details of the found users.
     * @author Caio Alves 
     */
    @Override @Transactional(readOnly = true)
    public Page<AdminUserDetailResponseDTO> findUserDetailsByRoleNameAndStatus(String roleName, StatusUsuario status, Pageable pageable){
        Page<Usuario> userPage = userRepository.findByPerfisCargoNomeAndStatus(roleName, status, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }
    
    /**
     * Maps a User entity to the detailed AdminUserDetailResponseDto.
     * @param user The User entity to map.
     * @return The corresponding AdminUserDetailResponseDto.
     * @Author Caio Alves
     */
    private AdminUserDetailResponseDTO mapUserToAdminDetailDto(Usuario user) {
        Pessoa person = user.getPessoa();
        Perfil activeProfile = user.getTipoAtivo();

        AdminUserContentResponseDTO contentDto = new AdminUserContentResponseDTO(
                user.getId(),
                activeProfile != null ? activeProfile.getTipo() : null,
                activeProfile != null ? activeProfile.getImagemUrl() : null,
                person != null ? person.getNomeCompleto() : null,
                person != null ? person.getCpf() : null,
                user.getTelefone(),
                user.getEmail(),
                person != null && person.getGenero() != null ? person.getGenero().toString() : null,
                person != null ? person.getDataNascimento() : null,
                user.getStatus() != null ? user.getStatus().toString() : null
        );

        List<AdminProfileListResponseDTO> profileListDto = user.getPerfis().stream()
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
                        specificType = ps.getTipo(); 
                    
                    } else if (profile instanceof AdminProfile) {
                        AdminProfile ap = (AdminProfile) profile;
                        displayType = "Administrador";
                        specificType = ap.getTipo();
                        
                    
                    } else {
                        displayType = profile.getTipo(); 
                    }
                    
                    return new AdminProfileListResponseDTO(
                            profile.getId(),
                            displayType, 
                            profile.getImagemUrl(),
                            specificType  
                    );
                })
                .collect(Collectors.toList());

        return new AdminUserDetailResponseDTO(contentDto, profileListDto);
    }
}
