package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.response.ItemAvaliacaoGeralResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.ItemAvaliacaoGeral;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interface de serviço para gerenciar as operações de negócio referentes aos Itens de Avaliação Geral.
 *
 * @author Andesson Reis
 */
public interface IItemAvaliacaoGeralService {

    /**
     * Salva um novo item de avaliação geral vinculado a uma solicitação.
     *
     * @param obj O objeto da entidade {@link ItemAvaliacaoGeral} a ser salvo.
     * @param solicitacaoId O UUID da solicitação à qual o item pertence.
     * @return O DTO contendo os dados do item salvo.
     */
    ItemAvaliacaoGeralResponseDTO save(ItemAvaliacaoGeral obj, UUID solicitacaoId);

    /**
     * Atualiza um item de avaliação geral existente.
     *
     * @param obj O objeto com os dados atualizados.
     * @param itemAvaliacaoGeralId O ID do item a ser atualizado.
     */
    void update(ItemAvaliacaoGeral obj, Long itemAvaliacaoGeralId);

    /**
     * Lista todos os itens de avaliação geral vinculados a uma solicitação específica.
     *
     * @param solicitacaoId O UUID da solicitação.
     * @param pageable Informações de paginação.
     * @return Uma lista de DTOs dos itens encontrados.
     */
    List<ItemAvaliacaoGeralResponseDTO> findAllBySolicitacaoId(UUID solicitacaoId, Pageable pageable);

}