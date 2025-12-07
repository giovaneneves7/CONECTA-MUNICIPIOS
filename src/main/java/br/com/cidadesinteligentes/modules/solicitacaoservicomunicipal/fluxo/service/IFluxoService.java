package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.AtividadeFluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoAtividadeResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.AtividadeFluxo;

import java.util.List;
import java.util.UUID;

/**
 * Interface de serviço para o gerenciamento de Fluxos e suas respectivas
 * atividades.
 *
 * @author Giovane Neves, Andesson Reis
 */
public interface IFluxoService {

    /**
     * Cria um novo fluxo de processo no sistema.
     *
     * @param dto O DTO contendo as informações para a criação do fluxo.
     * @return O DTO com os dados do fluxo criado.
     */
    FluxoResponseDTO criarFluxo(final FluxoRequestDTO dto);

    /**
     * Cria e associa uma nova atividade a um fluxo existente.
     *
     * @param dto O DTO contendo as informações da atividade e o vínculo com o
     *            fluxo.
     * @return O DTO com os dados da atividade criada.
     */
    FluxoAtividadeResponseDTO criarAtividadeFluxo(final AtividadeFluxoRequestDTO dto);

    /**
     * Recupera a lista completa de todos os fluxos cadastrados.
     *
     * @return Uma lista de DTOs contendo os dados completos dos fluxos.
     */
    List<FluxoDadosCompletosResponseDTO> buscarTodosFluxos();

    /**
     * Busca os detalhes completos de um fluxo pelo seu identificador único.
     *
     * @param id O UUID do fluxo.
     * @return O DTO com os dados completos do fluxo.
     */
    FluxoDadosCompletosResponseDTO buscarFluxoPorId(final UUID id);

    /**
     * Busca o fluxo associado a um determinado serviço municipal.
     *
     * @param id O ID do serviço municipal.
     * @return O DTO com os dados completos do fluxo encontrado.
     */
    FluxoDadosCompletosResponseDTO buscarFluxoPorServicoMunicipalId(final Long id);

    /**
     * Recupera a primeira atividade (atividade inicial) de um fluxo específico.
     *
     * @param fluxoId O UUID do fluxo.
     * @return A entidade {@link AtividadeFluxo} correspondente à primeira
     *         atividade.
     */
    AtividadeFluxo buscarPrimeiraAtividadeDoFluxoPorFluxoId(final UUID fluxoId);

    

}