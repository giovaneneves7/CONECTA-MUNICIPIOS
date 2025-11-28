package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.model;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.model.Document;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.enums.EvaluationItemStatus;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Represents a single item used for evaluating a submitted document.
 * <p>
 * This entity stores the reviewer's note and the current status (e.g., COMPLETED, INCOMPLETE)
 * for a specific part of the document review process. It maintains a Many-to-One relationship
 * with the {@link Document} it evaluates.
 *
 * @author Jorge Roberto
 */
@Entity
@Table(name = "evaluation_items")
@Getter @Setter
@NoArgsConstructor
public class EvaluationItem extends PersistenceEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "blueprintType", nullable = true)
    private String blueprintType;

    @Column(name = "note",  nullable = false)
    private String note;

    @Column(name = "status",  nullable = false)
    private EvaluationItemStatus status;

    /**
     * The Document to which this evaluation item belongs.
     * This establishes the Many-to-One relationship, linking the evaluation back to the document.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}
