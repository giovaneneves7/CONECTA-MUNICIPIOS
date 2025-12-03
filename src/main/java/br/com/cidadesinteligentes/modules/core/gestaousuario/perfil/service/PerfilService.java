package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissionResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileWithRoleResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfilePublicDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileResponseCurrentTypeDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.IRequestRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to {@link Perfil}.
 *
 * @author Jorge Roberto, Giovane Neves
 */
@RequiredArgsConstructor
@Service
public class PerfilService implements IPerfilService {

    private final IPerfilRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IRequestRepository requestRepository;
    private  final IUsuarioRepository userRepository;

    @Override @Transactional
    public ProfileWithRoleResponseDTO update(Perfil profile) {
        Perfil existing = this.repository.findById(profile.getId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        existing.setTipo(profile.getTipo());
        existing.setImagemUrl(profile.getImagemUrl());

        existing = repository.save(existing);

        return new ProfileWithRoleResponseDTO(
                existing.getId(),
                existing.getRole().getNome(),
                existing.getTipo(),
                existing.getImagemUrl()
        );
    }

    @Override @Transactional
    public void delete(UUID id) {
        Perfil profile = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (profile.getUsuario() != null) {
            throw new BusinessException(BusinessExceptionMessage.CLASS_IN_USE.getMessage());
        }
        repository.delete(profile);
    }

    @Override @Transactional(readOnly = true)
    public ProfilePublicDataResponseDTO findById(UUID id) {

        Perfil found = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return new ProfilePublicDataResponseDTO(
                found.getId(),
                found.getTipo(),
                found.getImagemUrl(),
                found.getUsuario().getUsername(),
                found.getUsuario().getPessoa().getCpf(),
                found.getUsuario().getTelefone(),
                found.getUsuario().getEmail(),
                found.getUsuario().getPessoa().getGenero(),
                found.getUsuario().getPessoa().getDataNascimento()
                );

    }

    @Override @Transactional(readOnly = true)
    public List<ProfileWithRoleResponseDTO> getAllProfiles(Pageable pageable) {

        Page<Perfil> profiles = repository.findAll(pageable);
        return profiles.stream()
                .map(profile -> new ProfileWithRoleResponseDTO(
                        profile.getId(),
                        profile.getRole().getNome(),
                        profile.getTipo(),
                        profile.getImagemUrl()
                ))
                .toList();

    }

    /**
     * Find all requests linked to the user id passed as a parameter
     *
     * @author Giovane Neves
     * @param userId The userId linked to the requests
     * @return A pageable list of requests linked to the user id passed as a parameter
     */
    @Override
    public Page<RequestResponseDTO> findAllRequestsByProfileId(UUID userId, Pageable pageable) {

        return this.requestRepository.findAllByProfileId(userId, pageable)
                .map(request -> this.objectMapperUtil.mapToRecord(request, RequestResponseDTO.class));

    }

    @Override @Transactional
    public ProfileResponseCurrentTypeDTO changeActiveProfile(UUID userId, String newActiveType) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Perfil newActiveProfile = user.getPerfis().stream()
                .filter(p -> p.getTipo().equals(newActiveType))
                .findFirst()
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_PROFILE.getMessage()));

        user.setPerfilAtivo(newActiveProfile);
        userRepository.save(user);

        return new ProfileResponseCurrentTypeDTO(newActiveType, newActiveProfile.getRole().getNome());
    }

    @Override @Transactional(readOnly = true)
    public List<PermissionResponseDTO> findAllPermissionsByProfile(UUID profileId, Pageable pageable) {
        Perfil profile = repository.findById(profileId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Cargo role = profile.getRole();

        if (role == null || role.getPermissoes() == null || role.getPermissoes().isEmpty())  {
            return Collections.emptyList();
        }

        return role.getPermissoes().stream()
                .map(permission -> this.objectMapperUtil.mapToRecord(permission, PermissionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
