package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.AtividadeFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IAtividadeFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.AcompanhamentoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.AcompanhamentoStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Acompanhamento;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.repository.IAcompanhamentoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.repository.IAtividadeRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.ISolicitacaoRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável por gerenciar as entidades de {@link Acompanhamento}.
 * <p>
 * Lida com a criação, atualização, recuperação e exclusão de acompanhamentos de
 * solicitações,
 * além de mapeá-los para seus respectivos DTOs.
 * </p>
 * <p>
 * Funcionalidades principais:
 * <ul>
 * <li>Salvar e atualizar acompanhamentos vinculados a uma solicitação.</li>
 * <li>Recuperar todos os acompanhamentos ou buscar por ID.</li>
 * <li>Excluir acompanhamento por ID.</li>
 * <li>Gerenciar a transição de fluxo (concluir atual e ativar próximo).</li>
 * </ul>
 * </p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@Service
@RequiredArgsConstructor
public class AcompanhamentoService implements IAcompanhamentoService {

        private final ObjectMapperUtil objectMapperUtil;
        private final ISolicitacaoRepository solicitacaoRepository;
        private final IAcompanhamentoRepository acompanhamentoRepository;
        private final IAtividadeRepository atividadeRepository;
        private final IAtividadeFluxoRepository atividadeFluxoRepository;

        @Override
        public AcompanhamentoResponseDTO save(final AcompanhamentoRequestDTO dto) {

                Solicitacao solicitacao = this.solicitacaoRepository.findById(dto.solicitacaoId())
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                Atividade atividade = this.atividadeRepository.findById(dto.atividadeId())
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                Acompanhamento acompanhamento = this.objectMapperUtil.map(dto, Acompanhamento.class);

                acompanhamento.setSolicitacao(solicitacao);
                acompanhamento.setAtividade(atividade);

                if (dto.status() != null) {
                        acompanhamento.setStatus(dto.status());
                } else {
                        acompanhamento.setStatus(AcompanhamentoStatus.PENDENTE);
                }

                return this.objectMapperUtil.mapToRecord(acompanhamentoRepository.save(acompanhamento),
                                AcompanhamentoResponseDTO.class);
        }

        @Override
        @Transactional
        public AcompanhamentoResponseDTO update(final AcompanhamentoAtualizacaoRequestDTO dto) {

                Acompanhamento acompanhamentoExistente = this.acompanhamentoRepository.findById(dto.id())
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                acompanhamentoExistente.setCodigo(dto.codigo());
                acompanhamentoExistente.setStatus(dto.status());

                if (dto.atividadeId() != null
                                && !dto.atividadeId().equals(acompanhamentoExistente.getAtividade().getId())) {
                        Atividade novaAtividade = this.atividadeRepository.findById(dto.atividadeId())
                                        .orElseThrow(() -> new BusinessException("Atividade informada não encontrada"));
                        acompanhamentoExistente.setAtividade(novaAtividade);
                }

                if (dto.solicitacaoId() != null
                                && !dto.solicitacaoId().equals(acompanhamentoExistente.getSolicitacao().getId())) {
                        Solicitacao novaSolicitacao = this.solicitacaoRepository.findById(dto.solicitacaoId())
                                        .orElseThrow(() -> new BusinessException(
                                                        "Solicitação informada não encontrada"));
                        acompanhamentoExistente.setSolicitacao(novaSolicitacao);
                }

                return this.objectMapperUtil.mapToRecord(acompanhamentoRepository.save(acompanhamentoExistente),
                                AcompanhamentoResponseDTO.class);
        }

        @Override
        public AcompanhamentoResponseDTO findById(final UUID id) {
                Acompanhamento acompanhamento = this.acompanhamentoRepository.findById(id)
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                return this.objectMapperUtil.mapToRecord(acompanhamento, AcompanhamentoResponseDTO.class);
        }

        @Override
        public List<AcompanhamentoResponseDTO> findAll(final Pageable pageable) {

                return this.acompanhamentoRepository.findAll(pageable)
                                .stream()
                                .map(acompanhamento -> this.objectMapperUtil.mapToRecord(acompanhamento,
                                                AcompanhamentoResponseDTO.class))
                                .toList();
        }

        @Override
        public void delete(UUID id) {
                Acompanhamento acompanhamento = this.acompanhamentoRepository.findById(id)
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                this.acompanhamentoRepository.delete(acompanhamento);
        }

        @Transactional
        @Override
        public void concluirAcompanhamentoAtualEAtivarProximo(Solicitacao solicitacao, boolean aprovado) {

                // Busca o acompanhamento pendente mais recente para esta solicitação
                Acompanhamento acompanhamentoAtual = this.acompanhamentoRepository
                                .findFirstBySolicitacaoIdAndAcompanhamentoStatusOrderByDataCriacaoDesc(
                                                solicitacao.getId(), AcompanhamentoStatus.PENDENTE)
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                // INFO: Atualiza o status atual
                acompanhamentoAtual.setStatus(aprovado
                                ? AcompanhamentoStatus.CONCLUIDO
                                : AcompanhamentoStatus.REJEITADO);
                this.acompanhamentoRepository.save(acompanhamentoAtual);

                if (!aprovado)
                        return;

                // INFO: Busca a próxima atividade do fluxo
                AtividadeFluxo atividadeFluxoAtual = this.atividadeFluxoRepository.findByFluxoIdAndAtividadeId(
                                solicitacao.getServicoMunicipal().getFluxo().getId(),
                                acompanhamentoAtual.getAtividade().getId())
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                AtividadeFluxo proximaAtividadeFluxo = this.atividadeFluxoRepository
                                .buscarProximaAtividadePorFluxo(atividadeFluxoAtual.getFluxo().getId(),
                                                atividadeFluxoAtual.getOrdem())
                                .orElse(null);

                if (proximaAtividadeFluxo == null)
                        return;

                // INFO: Cria um novo acompanhamento
                Acompanhamento proximoAcompanhamento = new Acompanhamento();
                proximoAcompanhamento.setAtividade(proximaAtividadeFluxo.getAtividade());
                proximoAcompanhamento.setCodigo(proximaAtividadeFluxo.getAtividade().getCodigo());
                proximoAcompanhamento.setSolicitacao(solicitacao);
                proximoAcompanhamento.setStatus(AcompanhamentoStatus.PENDENTE);

                this.acompanhamentoRepository.save(proximoAcompanhamento);
        }

}