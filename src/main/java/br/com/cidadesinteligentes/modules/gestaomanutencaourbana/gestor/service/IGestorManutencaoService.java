package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorRetornarIdResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IGestorManutencaoService {

    /**
     * Cria um novo perfil de gestor.
     * @param dto Dados do novo gestor.
     * @return DTO com os dados do gestor salvo.
     */
    GestorResponseDTO save(GestorCriarRequestDTO dto);

    /**
     * Lista todos os gestores cadastrados.
     * @return Lista de DTOs de resposta.
     */
    List<GestorResponseDTO> findAll();

    /**
     * Busca um gestor pelo seu ID.
     * @param id Identificador único do gestor (UUID).
     * @return DTO do gestor encontrado.
     */
    GestorResponseDTO findById(UUID id);

    /**
     * Atualiza os dados de um gestor existente.
     * @param dto Dados para atualização.
     * @return DTO do gestor atualizado.
     */
    GestorResponseDTO update(GestorAtualizarRequestDTO dto);

    /**
     * Remove um gestor pelo ID.
     * @param id Identificador do gestor a ser removido.
     * @return O ID do gestor que foi excluído.
     */
    public GestorRetornarIdResponseDTO delete(UUID id);

}