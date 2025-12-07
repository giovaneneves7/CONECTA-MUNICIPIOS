package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.enums.SolicitacaoStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;

/**
 * Repositório de acesso a dados para a entidade {@link Solicitacao}.
 * Fornece operações CRUD e métodos de interação com o banco de dados para as solicitações de serviço.
 *
 * @author Caio Alves, Giovane Neves, Andesson Reis
 */
@Repository
public interface ISolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {

    /**
     * Busca todas as solicitações vinculadas a um determinado ID de perfil.
     *
     * @param perfilId O ID do perfil do usuário.
     * @param pageable Informações de paginação.
     * @return Uma página de solicitações.
     */
    @Query("SELECT s FROM Solicitacao s WHERE s.perfil.id = :perfilId")
    Page<Solicitacao> findAllByPerfilId(@Param("perfilId") UUID perfilId, Pageable pageable);

    /**
     * Encontra uma lista paginada de solicitações que correspondem ao tipo especificado.
     * A consulta é gerada automaticamente pelo Spring Data JPA com base no nome do método.
     *
     * @param tipo O tipo da solicitação para filtrar.
     * @param pageable Informações de paginação e ordenação.
     * @return Uma página contendo as solicitações correspondentes ao tipo.
     * @author Caio Alves
     */
    Page<Solicitacao> findByTipo(String tipo, Pageable pageable);

    /**
     * Recupera uma lista paginada de solicitações filtrada pelo novo status registrado no histórico de status.
     * A consulta navega pelo relacionamento um-para-um com a entidade de histórico.
     *
     * @param historicoStatusNewStatus O novo status para filtrar as solicitações.
     * @param pageable Informações de paginação e ordenação.
     * @return Uma página contendo as solicitações correspondentes ao status.
     */
    Page<Solicitacao> findAllByHistoricoStatus_NewStatus(String historicoStatusNewStatus, Pageable pageable);

    /**
     * Recupera uma lista paginada de solicitações filtrada por uma lista de status registrados no histórico.
     *
     * @param statuses A lista de status alvo (ex: ["CONCLUIDO", "REJEITADO"]).
     * @param pageable Informações de paginação e ordenação.
     * @return Uma página contendo as solicitações que correspondem a qualquer um dos status fornecidos.
     */
    Page<Solicitacao> findAllByHistoricoStatus_NewStatusIn(List<String> statuses, Pageable pageable);

    /**
     * Recupera a primeira solicitação associada ao ID do serviço municipal fornecido,
     * ordenada pela data de criação em ordem decrescente.
     * <p>
     * Utilizado para encontrar a solicitação mais recente vinculada a um serviço.
     * </p>
     *
     * @param servicoMunicipalId O ID do Serviço Municipal.
     * @return Um Optional contendo a solicitação mais recente encontrada.
     */
    Optional<Solicitacao> findFirstByServicoMunicipalIdOrderByDataCriacaoDesc(Long servicoMunicipalId);

    /**
     * Encontra uma entidade de Solicitação pelo seu número de protocolo único.
     *
     * @param numeroProtocolo O número de protocolo da solicitação.
     * @return Um Optional contendo a solicitação encontrada.
     */
    Optional<Solicitacao> findByNumeroProtocolo(String numeroProtocolo);

    /**
     * Busca todas as solicitações vinculadas a um serviço municipal específico.
     *
     * @param servicoMunicipalId O ID do serviço municipal.
     * @return Lista de solicitações encontradas.
     */
    List<Solicitacao> findAllByServicoMunicipalId(Long servicoMunicipalId);

    Page<Solicitacao> findAllByStatusIn(List<SolicitacaoStatus> status, Pageable pageable);

    Page<Solicitacao> findByIdAndStatusIn(UUID id, List<SolicitacaoStatus> status, Pageable pageable);

}