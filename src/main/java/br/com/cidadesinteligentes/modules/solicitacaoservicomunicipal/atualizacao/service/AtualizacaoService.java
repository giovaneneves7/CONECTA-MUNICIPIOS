package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.ISolicitacaoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request.AtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.AtualizacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model.Atualizacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.repository.IAtualizacaoRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementação do serviço responsável por gerenciar as Atualizações de Solicitações.
 *
 * @author Giovane Neves, Andesson Reis
 */
@Service
@RequiredArgsConstructor
public class AtualizacaoService implements IAtualizacaoService {

    private final IAtualizacaoRepository atualizacaoRepository;
    private final ISolicitacaoRepository solicitacaoRepository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    public AtualizacaoResponseDTO save(final AtualizacaoRequestDTO dto) {

        Solicitacao solicitacao = this.solicitacaoRepository.findById(dto.solicitacaoId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Atualizacao atualizacao = this.objectMapperUtil.map(dto, Atualizacao.class);
        atualizacao.setSolicitacao(solicitacao);

        return this.objectMapperUtil.mapToRecord(atualizacaoRepository.save(atualizacao), AtualizacaoResponseDTO.class);

    }

    @Override
    public Page<AtualizacaoResponseDTO> findAll(Pageable pageable) {

        return this.atualizacaoRepository.findAll(pageable)
                .map(atualizacao -> this.objectMapperUtil.mapToRecord(atualizacao, AtualizacaoResponseDTO.class));

    }

}