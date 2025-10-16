package br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.service;

import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.response.GeneralEvaluationItemResponseDTO;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.model.GeneralEvaluationItem;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.repository.GeneralEvaluationItemRepository;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneralEvaluationItemService implements IGeneralEvaluationItemService {

    private final GeneralEvaluationItemRepository generalEvaluationItemRepository;
    private final RequestRepository requestRepository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override @Transactional
    public GeneralEvaluationItemResponseDTO save(GeneralEvaluationItem obj, UUID requestId) {
        Request request = this.requestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        obj.setRequest(request);

        GeneralEvaluationItem savedObj = this.generalEvaluationItemRepository.save(obj);

        request.getGeneralEvaluationItems().add(savedObj);


        this.requestRepository.save(request);

        return objectMapperUtil.mapToRecord(obj, GeneralEvaluationItemResponseDTO.class);
    }

    @Override @Transactional
    public void update(GeneralEvaluationItem obj, Long generalEvaluationItemId) {
        GeneralEvaluationItem newObj = this.generalEvaluationItemRepository.findById(generalEvaluationItemId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        newObj.setNote(obj.getNote());
        newObj.setStatus(obj.getStatus());
        this.generalEvaluationItemRepository.save(newObj);
    }

    @Override @Transactional(readOnly = true)
    public List<GeneralEvaluationItemResponseDTO> findAllByRequestId(UUID requestId, Pageable pageable) {
        Request request = this.requestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Page<GeneralEvaluationItem> objsPage = this.generalEvaluationItemRepository.findAllByRequestId(requestId, pageable);

        return objsPage.stream()
                .map(obj ->
                        objectMapperUtil.mapToRecord(obj, GeneralEvaluationItemResponseDTO.class))
                .toList();

    }
}
