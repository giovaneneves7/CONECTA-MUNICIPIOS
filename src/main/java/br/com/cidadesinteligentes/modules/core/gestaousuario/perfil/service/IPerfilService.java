package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request.PerfilAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.*;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interface do Service de Perfil
 */
public interface IPerfilService {

    /**
     * Atualiza um perfil existente.
     */
    PerfilComCargoResponseDTO update(PerfilAtualizarRequestDTO dto);

    /**
     * Deleta um perfil com base no ID do mesmo.
     */
    PerfilApenasIdResponseDTO delete(UUID id);

    /**
     * Encontra um perfil com base no ID fornecido.
     */
    PerfilDadosPublicosResponseDTO findById(UUID id);


    /**
     * Retorna a paginação de todos os perfis
     * */
    List<PerfilComCargoResponseDTO> findAll(Pageable pageable);

    /**
     * Encontra todas as requisições ligadas a um perfil de usuário, usando o ID do perfil, para buscar
     */
    Page<RequestResponseDTO> findAllRequestsByPerfilId(UUID perfilId, Pageable pageable);

    /**
     * Essa função atualiza o perfil ativo de um usuário, por outro perfil,
     * desde de que o usuário, tenha esse outro perfil
     */
    PerfilVerificarTipoAtivoResponseDTO changeActivePerfil(UUID usuarioId, String novoTipoAtivo);

    /**
     * Lista todas as permissões de um perfil
     * */
    List<PermissaoResponseDTO> findAllPermissoesByPerfil(UUID perfilId, Pageable pageable);

}
