package br.edu.ifba.conectairece.api.features.document.domain.model;

import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;
import br.edu.ifba.conectairece.api.features.evaluationItem.domain.model.EvaluationItem;
import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a document submitted as part of a {@link Requirement}.
 * <p>
 * This entity encapsulates all metadata and the lifecycle state of a single piece of
 * documentation, such as a PDF or an image file. Its state transitions (e.g., from PENDING
 * to APPROVED) are managed through explicit business methods to ensure consistency.
 * </p>
 *
 * @author Caio Alves, Andesson Reis
 */
@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends PersistenceEntity {

    /**
     * The original name of the uploaded file.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The file extension, stored separately for easier type identification.
     */
    @Column(nullable = false)
    private String fileExtension;

    /**
     * The URL pointing to the stored file, typically in a cloud storage service.
     */
    @Column(nullable = false)
    private String fileUrl;

    /**
     * A note or justification related to the document's review process.
     * Primarily used to store the reason for rejection.
     */
    @Column(columnDefinition = "TEXT")
    private String reviewNote;

    /**
     * An optional note provided by the user during the document submission.
     * This can include additional context or information about the document.
     */
    @Column(columnDefinition = "TEXT")
    private String submissionNote;

    /**
     * The current status of the document in the review lifecycle.
     * Defaults to PENDING upon creation.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status = DocumentStatus.PENDING;

    /**
     * The parent Requirement to which this document belongs.
     * A document cannot exist without being associated with a requirement.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requirement_id", nullable = false)
    private Requirement requirement;

    @OneToMany(mappedBy = "document")
    private List<EvaluationItem> evaluationItems = new ArrayList<>();

    public Document(String name, String fileExtension, String fileUrl, Requirement requirement, String submissionNote) {
        Assert.hasText(name, "Document name cannot be blank.");
        Assert.hasText(fileUrl, "File URL cannot be blank.");
        Assert.notNull(requirement, "Requirement cannot be null.");

        this.name = name;
        this.fileExtension = fileExtension;
        this.fileUrl = fileUrl;
        this.requirement = requirement;
        this.submissionNote = submissionNote;
    }
    /**
     * Approves the document, transitioning its status to APPROVED.
     * This action is only valid if the document is currently PENDING.
     *
     * @throws IllegalStateException if the document is not in PENDING status.
     */
    public void approve() {
        Assert.state(this.status == DocumentStatus.PENDING, "Cannot approve a document that is not in PENDING status.");
        this.status = DocumentStatus.APPROVED;
        this.reviewNote = null; // Clear any previous notes on approval.
    }

    /**
     * Rejects the document, transitioning its status to REJECTED.
     * This action is only valid if the document is currently PENDING.
     *
     * @param justification The mandatory reason for the rejection.
     * @throws IllegalStateException if the document is not in PENDING status.
     */
    public void reject(String justification) {
        Assert.state(this.status == DocumentStatus.PENDING, "Cannot reject a document that is not in PENDING status.");
        Assert.hasText(justification, "A justification is required to reject a document.");

        this.status = DocumentStatus.REJECTED;
        this.reviewNote = justification;
    }
}