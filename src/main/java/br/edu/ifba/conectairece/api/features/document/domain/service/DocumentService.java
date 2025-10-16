package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.document.domain.repository.DocumentRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class that implements the business logic for managing {@link Document} entities.
 * It orchestrates the document review process, delegating state changes to the domain entity itself.
 *
 * @author Andesson Reis 
 */
@Service
@RequiredArgsConstructor 
public class DocumentService implements IDocumentService {

    private final DocumentRepository documentRepository;
    private final ObjectMapperUtil objectMapperUtil; 
    @Override
    @Transactional
    public DocumentDetailResponseDTO approveDocument(UUID documentId) {
        Document document = findDocumentById(documentId);
        
        document.approve();

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    @Override
    @Transactional
    public DocumentDetailResponseDTO rejectDocument(UUID documentId, DocumentRejectionDTO rejectionDto) {
        Document document = findDocumentById(documentId);

        document.reject(rejectionDto.getJustification());

        Document savedDocument = documentRepository.save(document);

        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Private helper method to find a document by its ID or throw a standardized exception.
     *
     * @param documentId The UUID of the document to find.
     * @return The found {@link Document} entity.
     * @throws BusinessException if no document is found with the given ID.
     */
    private Document findDocumentById(UUID documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException("Document with ID " + documentId + " not found."));
    }
}