package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentWithStatusResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums.DocumentStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.AcompanhamentoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoFinalizadaRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoDetalhadaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.AtualizacaoResponseDTO;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Define as operações para o gerenciamento de entidades {@link Solicitacao}.
 * Fornece métodos para salvar, atualizar, recuperar e excluir solicitações,
 * além de consultas específicas de negócio.
 *
 * @author Caio, Giovane Neves, Andesson Reis
 */
public interface ISolicitacaoService {

    SolicitacaoResponseDTO save(SolicitacaoRequestDTO dto);

    SolicitacaoResponseDTO update(UUID id, SolicitacaoAtualizacaoRequestDTO dto);

    List<SolicitacaoResponseDTO> findAll(final Pageable pageable);

    SolicitacaoResponseDTO findById(UUID id);

    void delete(UUID id);

    Page<AcompanhamentoResponseDTO> listarAcompanhamentosPorSolicitacaoId(UUID id, Pageable pageable);

    Page<AtualizacaoResponseDTO> listarAtualizacoesPorSolicitacaoId(UUID id, Pageable pageable);

    /**
     * Recupera uma lista paginada de solicitações filtrada por um tipo específico.
     *
     * @param tipo O tipo para filtrar as solicitações (ex: "Solicitação de Alvará de Construção").
     * @param pageable Informações de paginação e ordenação.
     * @return Uma página contendo objetos {@link SolicitacaoResponseDTO} correspondentes ao tipo especificado.
     * @author Caio Alves
     */
    Page<SolicitacaoResponseDTO> buscarPorTipo(String tipo, Pageable pageable);

    /**
     * Recupera uma lista paginada de solicitações filtrada pelo último status registrado (novoStatus)
     * no Histórico de Status associado.
     *
     * @param novoStatusHistorico O status alvo (ex: "APROVADO", "PENDENTE") para filtrar as solicitações.
     * @param pageable Critérios de paginação e ordenação.
     * @return Uma {@link Page} de {@link SolicitacaoResponseDTO} contendo os resultados filtrados e paginados.
     */
    Page<SolicitacaoResponseDTO> listarPorNovoStatusHistorico(String novoStatusHistorico, Pageable pageable);

    /**
     * Recupera uma lista paginada de solicitações que atingiram um status final (CONCLUIDO ou REJEITADO).
     *
     * @param pageable Critérios de paginação e ordenação.
     * @return Uma página de {@link SolicitacaoDetalhadaResponseDTO} contendo as solicitações finalizadas.
     */
    Page<SolicitacaoDetalhadaResponseDTO> listarSolicitacoesFinalizadas(
            SolicitacaoFinalizadaRequestDTO filtro, 
            Pageable pageable
        );

    List<DocumentWithStatusResponseDTO> listarTodosDocumentosPorSolicitacaoId(UUID solicitacaoId);
    List<DocumentWithStatusResponseDTO> listarDocumentosDaSolicitacaoPorStatus(UUID solicitacaoId, DocumentStatus statusFilter);

}