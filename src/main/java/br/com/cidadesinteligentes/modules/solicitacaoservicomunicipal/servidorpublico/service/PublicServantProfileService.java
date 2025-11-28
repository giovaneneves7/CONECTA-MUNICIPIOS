package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.UserStatus;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Role;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.IRoleRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response.PublicServantRegisterResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.repository.IPublicServantProfileRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.User;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUserRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PublicServantProfileService implements IPublicServantProfileService{

    private final IUserRepository userRepository;
    private final IPublicServantProfileRepository publicServantRepository;
    private final IRoleRepository roleRepository;

    @Override @Transactional
    public PublicServantRegisterResponseDTO createPublicServantProfile(UUID userId, PublicServantProfile employee) {
        if (employee == null || userId == null) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_DATA.getMessage());
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())
        );

        if (user.getStatus() != UserStatus.ACTIVE){
            throw new BusinessException("User must be ACTIVE to be assigned a Public Servant profile.");
        }

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
