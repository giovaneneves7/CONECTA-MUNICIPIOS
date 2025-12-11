package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request.PerfilAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilApenasIdResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissaoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilDadosPublicosResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilVerificarTipoAtivoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.ISolicitacaoRepository;
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
 * Classe Service responsável pela parte lógica de {@link Perfil}.
 *
 * @author Jorge Roberto, Giovane Neves
 */
@RequiredArgsConstructor
@Service
public class PerfilService implements IPerfilService {

    private final IPerfilRepository repository;
    private final ObjectMapperUtil objectMapperUtil;
    private final ISolicitacaoRepository solicitacaoRepository;
    private  final IUsuarioRepository userRepository;

    @Override @Transactional
    public PerfilComCargoResponseDTO update(PerfilAtualizarRequestDTO dto) {

        Perfil existing = this.repository.findById(dto.id())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        existing.setTipo(dto.tipo());
        existing.setImagemUrl(dto.imagemUrl());

        existing = repository.save(existing);

        return new PerfilComCargoResponseDTO(
                existing.getId(),
                existing.getCargo().getNome(),
                existing.getTipo(),
                existing.getImagemUrl()
        );
    }

    @Override @Transactional
    public PerfilApenasIdResponseDTO delete(UUID id) {
        Perfil profile = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Usuario user = profile.getUsuario();

        if (user != null) {

            if (profile.equals(user.getTipoAtivo())) {
                throw new BusinessException(BusinessExceptionMessage.CLASS_IN_USE.getMessage());
            }

            user.getPerfis().remove(profile);
            userRepository.save(user);
        }
        repository.delete(profile);
        return new PerfilApenasIdResponseDTO(profile.getId());
    }

    @Override @Transactional(readOnly = true)
    public PerfilDadosPublicosResponseDTO findById(UUID id) {

        Perfil found = repository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return new PerfilDadosPublicosResponseDTO(
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
    public List<PerfilComCargoResponseDTO> findAll(Pageable pageable) {

        Page<Perfil> profiles = repository.findAll(pageable);
        return profiles.stream()
                .map(profile -> new PerfilComCargoResponseDTO(
                        profile.getId(),
                        profile.getCargo().getNome(),
                        profile.getTipo(),
                        profile.getImagemUrl()
                ))
                .toList();

    }

    @Override
    public Page<SolicitacaoResponseDTO> findAllRequestsByPerfilId(UUID perfilId, Pageable pageable) {

        return this.solicitacaoRepository.findAllByPerfilId(perfilId, pageable)
                .map(request -> this.objectMapperUtil.mapToRecord(request, SolicitacaoResponseDTO.class));

    }

    @Override @Transactional
    public PerfilVerificarTipoAtivoResponseDTO changeActivePerfil(UUID usuarioId, String novoTipoAtivo) {
        Usuario user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Perfil newActiveProfile = user.getPerfis().stream()
                .filter(p -> p.getTipo().equals(novoTipoAtivo))
                .findFirst()
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_PROFILE.getMessage()));

        user.setTipoAtivo(newActiveProfile);
        userRepository.save(user);

        return new PerfilVerificarTipoAtivoResponseDTO(novoTipoAtivo, newActiveProfile.getCargo().getNome());
    }

    @Override @Transactional(readOnly = true)
    public List<PermissaoResponseDTO> findAllPermissoesByPerfil(UUID perfilId, Pageable pageable) {
        Perfil profile = repository.findById(perfilId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Cargo role = profile.getCargo();

        if (role == null || role.getPermissoes() == null || role.getPermissoes().isEmpty())  {
            return Collections.emptyList();
        }

        return role.getPermissoes().stream()
                .map(permission -> this.objectMapperUtil.mapToRecord(permission, PermissaoResponseDTO.class))
                .collect(Collectors.toList());
    }
}
