package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.response.ItemAvaliacaoGeralResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.ItemAvaliacaoGeral;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.repository.IItemAvaliacaoGeralRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.ISolicitacaoRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço responsável por gerenciar os Itens de Avaliação Geral.
 *
 * @author Andesson Reis
 */
@Service
@RequiredArgsConstructor
public class ItemAvaliacaoGeralService implements IItemAvaliacaoGeralService {

    private final IItemAvaliacaoGeralRepository itemAvaliacaoGeralRepository;
    private final ISolicitacaoRepository solicitacaoRepository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public ItemAvaliacaoGeralResponseDTO save(ItemAvaliacaoGeral obj, UUID solicitacaoId) {
        Solicitacao solicitacao = this.solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        obj.setSolicitacao(solicitacao);

        ItemAvaliacaoGeral salvoObj = this.itemAvaliacaoGeralRepository.save(obj);

        solicitacao.getItensAvaliacaoGeral().add(salvoObj);

        this.solicitacaoRepository.save(solicitacao);

        return objectMapperUtil.mapToRecord(salvoObj, ItemAvaliacaoGeralResponseDTO.class);
    }

    @Override
    @Transactional
    public void update(ItemAvaliacaoGeral obj, Long itemAvaliacaoGeralId) {
        ItemAvaliacaoGeral novoObj = this.itemAvaliacaoGeralRepository.findById(itemAvaliacaoGeralId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        
        novoObj.setObservacao(obj.getObservacao());
        novoObj.setStatus(obj.getStatus());
        
        this.itemAvaliacaoGeralRepository.save(novoObj);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemAvaliacaoGeralResponseDTO> findAllBySolicitacaoId(UUID solicitacaoId, Pageable pageable) {
        // Verifica se a solicitação existe antes de buscar os itens
        if (!this.solicitacaoRepository.existsById(solicitacaoId)) {
             throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }

        Page<ItemAvaliacaoGeral> objsPage = this.itemAvaliacaoGeralRepository.findAllBySolicitacaoId(solicitacaoId, pageable);

        return objsPage.stream()
                .map(obj ->
                        objectMapperUtil.mapToRecord(obj, ItemAvaliacaoGeralResponseDTO.class))
                .toList();

    }
}