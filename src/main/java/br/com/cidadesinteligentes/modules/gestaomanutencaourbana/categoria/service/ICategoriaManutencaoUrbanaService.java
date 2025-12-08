package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaRetornarIdResponseDTO;

import java.util.List;

public interface ICategoriaManutencaoUrbanaService {

    /**
     * Cria uma nova categoria de manutenção urbana.
     * @param dto Dados da nova categoria.
     * @return DTO com os dados da categoria salva.
     */
    CategoriaResponseDTO save(CategoriaCriarRequestDTO dto);

    /**
     * Lista todas as categorias cadastradas.
     * @return Lista de DTOs de resposta.
     */
    List<CategoriaResponseDTO> findAll();

    /**
     * Busca uma categoria pelo seu ID.
     * @param id Identificador único da categoria.
     * @return DTO da categoria encontrada.
     */
    CategoriaResponseDTO findById(Long id);

    /**
     * Atualiza os dados de uma categoria existente.
     * @param dto Dados para atualização (contém o ID).
     * @return DTO da categoria atualizada.
     */
    CategoriaResponseDTO update(CategoriaAtualizarRequestDTO dto);

    /**
     * Remove uma categoria pelo ID.
     * @param id Identificador da categoria a ser removida.
     * @return O ID da categoria que foi excluída.
     */
    CategoriaRetornarIdResponseDTO delete(Long id);
}