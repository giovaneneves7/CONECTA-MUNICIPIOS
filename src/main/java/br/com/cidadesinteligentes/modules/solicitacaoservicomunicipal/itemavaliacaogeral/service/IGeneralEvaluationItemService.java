package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.response.GeneralEvaluationItemResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.GeneralEvaluationItem;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IGeneralEvaluationItemService {
    GeneralEvaluationItemResponseDTO save(GeneralEvaluationItem obj, UUID requestId);
    void update (GeneralEvaluationItem obj, Long generalEvaluationItemId);
    List<GeneralEvaluationItemResponseDTO> findAllByRequestId(UUID requestId, Pageable pageable);
}
