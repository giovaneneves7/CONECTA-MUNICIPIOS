package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.model.Document;

/**
 * Represents the lifecycle status of a {@link Document}.
 * <p>
 * Each status indicates a specific stage in the document review process, from initial
 * submission to final validation.
 * </p>
 *
 * @author Andesson Reis 
 */
public enum DocumentStatus {

    /**
     * The initial state of a document after being submitted. It is awaiting review by an administrator.
     */
    PENDING,

    /**
     * Indicates that the document has been reviewed and accepted as valid and sufficient.
     */
    APPROVED,

    /**
     * Indicates that the document has been reviewed and was found to be invalid or insufficient.
     * A justification is typically required when a document is moved to this state.
     */
    REJECTED,

    /**
     * Indicates that the document has been reviewed for Technical Responsible and requires additional information or corrections.
     * The submitter is expected to provide the necessary updates.
     */
    CORRECTION_SUGGESTED
}