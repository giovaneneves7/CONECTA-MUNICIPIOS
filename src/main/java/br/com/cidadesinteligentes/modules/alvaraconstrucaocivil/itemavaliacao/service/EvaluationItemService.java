package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.service;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.model.Document;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.repository.IDocumentRepository;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.response.EvaluationItemResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.model.EvaluationItem;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.repository.IEvaluationItemRepository;
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

@RequiredArgsConstructor
@Service
public class EvaluationItemService implements IEvaluationItemService {

    private final IEvaluationItemRepository evaluationItemRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IDocumentRepository documentRepository;

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
        newObj.setName(obj.getName());
        newObj.setBlueprintType(obj.getBlueprintType());
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
