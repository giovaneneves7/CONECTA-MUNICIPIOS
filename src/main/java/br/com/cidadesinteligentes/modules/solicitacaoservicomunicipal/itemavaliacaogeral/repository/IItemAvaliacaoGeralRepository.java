package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.ItemAvaliacaoGeral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositório para gerenciar operações de persistência de itens de avaliação geral.
 *
 * @author Andesson Reis
 */
public interface IItemAvaliacaoGeralRepository extends JpaRepository<ItemAvaliacaoGeral, Long> {

    /**
     * Busca todos os itens de avaliação geral vinculados a uma solicitação específica.
     *
     * @param solicitacaoId O UUID da solicitação.
     * @param pageable Informações de paginação.
     * @return Uma página de itens de avaliação geral.
     */
    Page<ItemAvaliacaoGeral> findAllBySolicitacaoId(UUID solicitacaoId, Pageable pageable);

}