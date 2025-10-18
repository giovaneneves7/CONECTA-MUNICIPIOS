package br.edu.ifba.conectairece.api.features.evaluationItem.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.document.domain.repository.DocumentRepository;
import br.edu.ifba.conectairece.api.features.evaluationItem.domain.dto.response.EvaluationItemResponseDTO;
import br.edu.ifba.conectairece.api.features.evaluationItem.domain.model.EvaluationItem;
import br.edu.ifba.conectairece.api.features.evaluationItem.domain.repository.EvaluationItemRepository;
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

@RequiredArgsConstructor
@Service
public class EvaluationItemService implements IEvaluationItemService {

    private final EvaluationItemRepository evaluationItemRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final DocumentRepository documentRepository;

    @Override @Transactional
    public EvaluationItemResponseDTO save(UUID documentId, EvaluationItem obj) {
        Document document = this.documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        obj.setDocument(document);

        EvaluationItem saveObj = this.evaluationItemRepository.save(obj);

        document.getEvaluationItems().add(saveObj);

        this.documentRepository.save(document);

        return objectMapperUtil.mapToRecord(obj, EvaluationItemResponseDTO.class);
    }

    @Override @Transactional
    public void update(UUID evaluationId, EvaluationItem obj) {
        EvaluationItem newObj = this.evaluationItemRepository.findById(evaluationId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        newObj.setNote(obj.getNote());
        newObj.setStatus(obj.getStatus());
        this.evaluationItemRepository.save(newObj);
    }

    @Override @Transactional(readOnly = true)
    public List<EvaluationItemResponseDTO> findAllByDocumentId(UUID documentId, Pageable pageable) {
        this.documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Page<EvaluationItem> objsPage = this.evaluationItemRepository.findAllByDocumentId(documentId, pageable);

        return objsPage.stream()
                .map(obj -> objectMapperUtil.mapToRecord(obj, EvaluationItemResponseDTO.class))
                .toList();
    }
}
