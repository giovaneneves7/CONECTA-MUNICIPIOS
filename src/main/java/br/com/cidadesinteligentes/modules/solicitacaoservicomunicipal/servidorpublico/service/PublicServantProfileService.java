package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response.PublicServantRegisterResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.repository.IPublicServantProfileRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PublicServantProfileService implements IPublicServantProfileService{

    private final IUsuarioRepository userRepository;
    private final IPublicServantProfileRepository publicServantRepository;
    private final ICargoRepository roleRepository;

    @Override @Transactional
    public PublicServantRegisterResponseDTO createPublicServantProfile(UUID userId, PublicServantProfile employee) {
        if (employee == null || userId == null) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_DATA.getMessage());
        }

        Usuario user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage())
        );

        if (user.getStatus() != StatusUsuario.ATIVO){
            throw new BusinessException("User must be ACTIVE to be assigned a Public Servant profile.");
        }

        boolean alreadyHasPublicServantProfile = user.getPerfis().stream().anyMatch(p -> p instanceof PublicServantProfile);

        if (alreadyHasPublicServantProfile) {
            throw new BusinessException(BusinessExceptionMessage.USER_ALREADY_HAS_THIS_PROFILE.getMessage());
        }

        Cargo role = new Cargo();
        role.setNome("ROLE_PUBLIC_SERVANT");
        role.setDescricao("Role for public servant");
        roleRepository.save(role);

        employee.setRole(role);
        employee.setUsuario(user);

        employee = publicServantRepository.save(employee);

        user.getPerfis().add(employee);

        if (user.getPerfilAtivo() == null) {
            user.setPerfilAtivo(employee);
        }

        userRepository.save(user);

        return new PublicServantRegisterResponseDTO(employee.getEmployeeId());
    }
}
