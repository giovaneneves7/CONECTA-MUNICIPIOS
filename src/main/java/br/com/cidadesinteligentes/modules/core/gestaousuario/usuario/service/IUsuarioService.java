package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUsuarioService {
    UsuarioResponseDTO findById(final UUID id);
    List<PerfilComCargoResponseDTO> getUsuarioPerfis(final UUID id, final Pageable pageable);
    PerfilComCargoResponseDTO findPerfilAtivoByUsuarioId(final UUID id);
    List<UsuarioResponseDTO> findAll(final Pageable pageable);
    void updateStatusUsuario(UUID userId, StatusUsuario newStatus);
}
