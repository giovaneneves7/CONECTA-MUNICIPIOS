package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioCompletoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUsuarioService {
    UsuarioResponseDTO findById(final UUID id);
    List<PerfilComCargoResponseDTO> getUsuarioPerfis(final UUID id, final Pageable pageable);
    PerfilComCargoResponseDTO findPerfilAtivoByUsuarioId(final UUID id);
    List<UsuarioResponseDTO> findAll(final Pageable pageable);
    UsuarioResponseDTO updateStatusUsuario(final UUID userId, StatusUsuario novoStatus);
    Page<UsuarioCompletoResponseDTO> findAllDetalhesUsuarios(final Pageable pageable);
    Page<UsuarioCompletoResponseDTO> findDetalhesUsuarioByNomeCargo(final String nomeCargo, Pageable pageable);
    Page<UsuarioCompletoResponseDTO> findDetalhesUsuariosByStatus(final StatusUsuario status, Pageable pageable);
    Page<UsuarioCompletoResponseDTO> findDetalhesUsuarioByNomeOuCpf(final String termo, Pageable pageable);
    Page<UsuarioCompletoResponseDTO> findDetalhesUsuarioByNomeCargoEStatus(final String nomeCargo, StatusUsuario status, Pageable pageable);
}
