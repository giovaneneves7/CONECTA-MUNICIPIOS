package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for requesting the approval of a public servant's document.
 * <p>
 * This DTO is used when a public servant needs to approve a document within the system.
 * It includes:
 * <ul>
 *   <li>{@code documentId} - The UUID of the document to approve (required).</li>
 *   <li>{@code publicServantProfileId} - The UUID of the public servant profile (required).</li>
 *   <li>{@code comment} - An optional comment related to the approval.</li>
 * </ul>
 * </p>
 * 
 * @param documentId           the unique identifier of the document to approve; must not be null
 * @param publicServantProfileId the unique identifier of the public servant profile; must not be null
 * @param comment              optional comment provided during approval
 * 
 * 
 * Author: Andesson Reis
 */
public record PublicServantApproveDocumentRequestDTO(
        @NotNull UUID documentId,
        @NotNull UUID publicServantProfileId,
        String comment
) {}
