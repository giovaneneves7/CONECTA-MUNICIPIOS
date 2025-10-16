package br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.service;

import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.response.GeneralEvaluationItemResponseDTO;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.model.GeneralEvaluationItem;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IGeneralEvaluationItemService {
    GeneralEvaluationItemResponseDTO save(GeneralEvaluationItem obj, UUID requestId);
    void update (GeneralEvaluationItem obj, Long generalEvaluationItemId);
    List<GeneralEvaluationItemResponseDTO> findAllByRequestId(UUID requestId, Pageable pageable);
}
