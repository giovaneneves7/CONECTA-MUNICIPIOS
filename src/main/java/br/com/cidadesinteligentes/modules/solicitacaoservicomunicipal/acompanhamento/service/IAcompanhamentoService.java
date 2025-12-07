package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.AcompanhamentoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interface de serviço para gerenciar as operações de negócio referentes aos Acompanhamentos.
 *
 * @author Giovane Neves, Andesson Reis
 */
public interface IAcompanhamentoService {

    AcompanhamentoResponseDTO save(final AcompanhamentoRequestDTO dto);
    AcompanhamentoResponseDTO update(final AcompanhamentoAtualizacaoRequestDTO dto);
    AcompanhamentoResponseDTO findById(final UUID id);
    List<AcompanhamentoResponseDTO> findAll(Pageable pageable);
    void delete(final UUID id);
    void concluirAcompanhamentoAtualEAtivarProximo(final Solicitacao solicitacao, boolean aprovado);
}