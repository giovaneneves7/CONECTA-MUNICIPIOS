package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.repository.IConstructionLicenseRequirementRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentWithStatusResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums.DocumentStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.AcompanhamentoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.repository.IAcompanhamentoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.AtualizacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.repository.IAtualizacaoRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IServicoMunicipalRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.enums.SolicitacaoStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoDetalhadaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.evento.SolicitacaoCriadaEvento;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.ISolicitacaoRepository;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar as entidades {@link Solicitacao}.
 * Lida com a criação, atualização, recuperação e exclusão de solicitações de
 * serviço,
 * e mapeia-as para seus respectivos DTOs.
 *
 * Funcionalidades principais:
 * - Salvar e atualizar solicitações vinculadas a um serviço municipal
 * - Recuperar todas as solicitações ou buscar por ID
 * - Excluir solicitações por ID
 *
 * @author Caio Alves, Giovane Neves, Andesson Reis
 */
@Service
@RequiredArgsConstructor
public class SolicitacaoService implements ISolicitacaoService {

        private final ISolicitacaoRepository solicitacaoRepository;
        private final IServicoMunicipalRepository servicoMunicipalRepository;
        private final ObjectMapperUtil objectMapperUtil;
        private final IPerfilRepository perfilRepository;
        private final IAcompanhamentoRepository acompanhamentoRepository;
        private final IAtualizacaoRepository atualizacaoRepository;
        private final ApplicationEventPublisher eventPublisher;
        private final IConstructionLicenseRequirementRepository requerimentoRepository;

        /**
         * Salva uma nova solicitação para um determinado serviço municipal.
         *
         * @param dto dados da solicitação contendo número de protocolo, tipo e ID do
         *            serviço relacionado.
         * @return DTO com as informações da solicitação salva.
         */
        @Override
        @Transactional
        public SolicitacaoResponseDTO save(final SolicitacaoRequestDTO dto) {

                ServicoMunicipal servico = servicoMunicipalRepository.findById(dto.servicoMunicipalId())
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                Perfil perfil = perfilRepository.findById(dto.perfilId())
                                .orElseThrow(() -> new BusinessException("Perfil não encontrado"));

                Solicitacao solicitacao = new Solicitacao();
                solicitacao.setNumeroProtocolo(dto.numeroProtocolo());
                solicitacao.setDataPrevisaoConclusao(dto.dataPrevisaoConclusao());
                solicitacao.setTipo(dto.tipo());
                solicitacao.setObservacao(dto.observacao());
                solicitacao.setServicoMunicipal(servico);
                solicitacao.setPerfil(perfil);

                solicitacao.setStatus(SolicitacaoStatus.PENDENTE);

                Solicitacao salva = solicitacaoRepository.save(solicitacao);

                eventPublisher.publishEvent(new SolicitacaoCriadaEvento(salva));

                return objectMapperUtil.mapToRecord(solicitacao, SolicitacaoResponseDTO.class);
        }

        /**
         * Atualiza uma solicitação existente no banco de dados.
         *
         * @param id  identificador da solicitação a ser atualizada.
         * @param dto dados contendo os detalhes atualizados da solicitação.
         * @return DTO com as informações da solicitação atualizada.
         */
        @Override
        public SolicitacaoResponseDTO update(UUID id, SolicitacaoAtualizacaoRequestDTO dto) {
                Solicitacao solicitacao = solicitacaoRepository.findById(id)
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

                solicitacao.setNumeroProtocolo(dto.numeroProtocolo());
                solicitacao.setDataPrevisaoConclusao(dto.dataPrevisaoConclusao());
                solicitacao.setTipo(dto.tipo());
                solicitacao.setObservacao(dto.observacao());

                ServicoMunicipal servico = servicoMunicipalRepository.findById(dto.servicoMunicipalId())
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));
                solicitacao.setServicoMunicipal(servico);

                Solicitacao atualizada = solicitacaoRepository.save(solicitacao);

                return objectMapperUtil.mapToRecord(atualizada, SolicitacaoResponseDTO.class);
        }

        /**
         * Recupera todas as solicitações do banco de dados.
         *
         * @param pageable Parâmetros de paginação.
         * @return lista de DTOs de solicitação.
         */
        @Override
        public List<SolicitacaoResponseDTO> findAll(final Pageable pageable) {

                Page<Solicitacao> solicitacoes = solicitacaoRepository.findAll(pageable);

                return solicitacoes.stream()
                                .map(solicitacao -> this.objectMapperUtil.mapToRecord(solicitacao,
                                                SolicitacaoResponseDTO.class))
                                .toList();

        }

        /**
         * Busca uma solicitação pelo seu identificador.
         *
         * @param id ID da solicitação.
         * @return DTO da solicitação, se encontrada.
         */
        @Override
        public SolicitacaoResponseDTO findById(final UUID id) {

                return solicitacaoRepository.findById(id)
                                .map(solicitacao -> this.objectMapperUtil.mapToRecord(solicitacao,
                                                SolicitacaoResponseDTO.class))
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));

        }

        /**
         * Exclui uma solicitação pelo seu identificador.
         *
         * @param id ID da solicitação.
         */
        @Override
        public void delete(UUID id) {
                Solicitacao solicitacao = this.solicitacaoRepository.findById(id)
                                .orElseThrow(() -> new BusinessException(
                                                BusinessExceptionMessage.NOT_FOUND.getMessage()));
                solicitacaoRepository.delete(solicitacao);
        }

        @Override
        public Page<AcompanhamentoResponseDTO> listarAcompanhamentosPorSolicitacaoId(UUID id, Pageable pageable) {

                return this.acompanhamentoRepository.findAllBySolicitacaoId(id, pageable)
                                .map(acompanhamento -> this.objectMapperUtil.mapToRecord(acompanhamento,
                                                AcompanhamentoResponseDTO.class));

        }

        @Override
        public Page<AtualizacaoResponseDTO> listarAtualizacoesPorSolicitacaoId(UUID id, Pageable pageable) {

                return this.atualizacaoRepository.findBySolicitacaoId(id, pageable)
                                .map(atualizacao -> this.objectMapperUtil.mapToRecord(atualizacao,
                                                AtualizacaoResponseDTO.class));

        }

        /**
         * Busca e recupera uma lista paginada de solicitações filtradas pelo tipo
         * especificado.
         *
         * @param tipo     O tipo usado para filtrar as solicitações.
         * @param pageable Os parâmetros de paginação e ordenação.
         * @return Um objeto Page contendo a lista filtrada e paginada de
         *         SolicitacaoResponseDTO.
         * @author Caio Alves
         */
        @Override
        public Page<SolicitacaoResponseDTO> buscarPorTipo(String tipo, Pageable pageable) {
                Page<Solicitacao> paginaSolicitacoes = solicitacaoRepository.findByTipo(tipo, pageable);
                return paginaSolicitacoes.map(
                                solicitacao -> objectMapperUtil.mapToRecord(solicitacao, SolicitacaoResponseDTO.class));
        }

        @Override
        @Transactional(readOnly = true)
        public Page<SolicitacaoResponseDTO> listarPorNovoStatusHistorico(String novoStatusHistorico,
                        Pageable pageable) {
                Page<Solicitacao> paginaSolicitacoes = solicitacaoRepository.findAllByHistoricoStatus_NewStatus(
                                novoStatusHistorico,
                                pageable);
                return paginaSolicitacoes.map(
                                solicitacao -> this.objectMapperUtil.mapToRecord(
                                                solicitacao, SolicitacaoResponseDTO.class));
        }

        @Override
        @Transactional(readOnly = true)
        public Page<SolicitacaoDetalhadaResponseDTO> listarSolicitacoesFinalizadas(Pageable pageable) {

                Page<Solicitacao> paginaSolicitacoes = solicitacaoRepository.findAllByHistoricoStatus_NewStatusIn(
                                List.of("COMPLETE", "REJECTED"),
                                pageable);

                return paginaSolicitacoes.map(solicitacao -> {
                        String valorStatus = null;
                        if (solicitacao.getHistoricoStatus() != null) {
                                valorStatus = solicitacao.getHistoricoStatus().getNewStatus();
                        }

                        SolicitacaoDetalhadaResponseDTO baseDto = objectMapperUtil.mapToRecord(
                                        solicitacao,
                                        SolicitacaoDetalhadaResponseDTO.class);

                        return new SolicitacaoDetalhadaResponseDTO(
                                        baseDto.id(),
                                        baseDto.numeroProtocolo(),
                                        baseDto.dataCriacao(),
                                        baseDto.dataPrevisaoConclusao(),
                                        baseDto.dataAtualizacao(),
                                        baseDto.tipo(),
                                        baseDto.observacao(),
                                        baseDto.servicoMunicipalId(),
                                        valorStatus,
                                        solicitacao.getPerfil().getUsuario().getPessoa().getNomeCompleto(),
                                        solicitacao.getPerfil().getUsuario().getPessoa().getCpf());
                });
        }

        @Override
        @Transactional(readOnly = true)
        public List<DocumentWithStatusResponseDTO> listarDocumentosAprovadosPorSolicitacaoId(UUID solicitacaoId) {
                Solicitacao solicitacao = solicitacaoRepository
                                .findById(solicitacaoId)
                                .orElseThrow(() -> new BusinessException(
                                                "Solicitação não encontrada com ID: " + solicitacaoId));

                ServicoMunicipal servicoMunicipal = solicitacao.getServicoMunicipal();
                if (servicoMunicipal == null) {
                        throw new BusinessException("A solicitação não está associada a um Serviço Municipal.");
                }

                ConstructionLicenseRequirement requerimento = requerimentoRepository
                                .findById(servicoMunicipal.getId())
                                .orElseThrow(() -> new BusinessException(
                                                "Nenhum Requerimento encontrado para o Serviço Municipal ID: "
                                                                + servicoMunicipal.getId()));

                return requerimento
                                .getDocuments()
                                .stream()
                                .filter(documento -> documento.getStatus() == DocumentStatus.APPROVED)
                                .map(
                                                doc -> new DocumentWithStatusResponseDTO(doc.getId(), doc.getName(),
                                                                doc.getFileExtension(),
                                                                doc.getFileUrl(), doc.getStatus()))
                                .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<DocumentWithStatusResponseDTO> listarTodosDocumentosPorSolicitacaoId(UUID solicitacaoId) {

                Solicitacao solicitacao = solicitacaoRepository
                                .findById(solicitacaoId)
                                .orElseThrow(() -> new BusinessException(
                                                "Solicitação não encontrada com ID: " + solicitacaoId));

                ServicoMunicipal servicoMunicipal = solicitacao.getServicoMunicipal();
                if (servicoMunicipal == null) {
                        throw new BusinessException("A solicitação não está associada a um Serviço Municipal.");
                }

                ConstructionLicenseRequirement requerimento = requerimentoRepository
                                .findById(servicoMunicipal.getId())
                                .orElseThrow(() -> new BusinessException(
                                                "Nenhum Requerimento encontrado para o Serviço Municipal ID: "
                                                                + servicoMunicipal.getId()));

                return requerimento
                                .getDocuments()
                                .stream()
                                .map(doc -> new DocumentWithStatusResponseDTO(doc.getId(), doc.getName(),
                                                doc.getFileExtension(),
                                                doc.getFileUrl(), doc.getStatus()))
                                .collect(Collectors.toList());
        }
}