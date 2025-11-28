package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for receiving document data in API **update** requests.
 * <p>
 * Used when updating an existing document via PUT/PATCH.
 * This DTO mirrors the {@link DocumentRequestDTO} to ensure
 * compatibility with the service layer and validation.
 * <p>
 * Contains:
 * <ul>
 * <li>Document name.</li>
 * <li>File extension.</li>
 * <li>File storage URL.</li>
 * <li>Submission note (optional).</li>
 * </ul>
 * <p>
 * Validation:
 * <ul>
 * <li>Key fields are mandatory and cannot be blank.</li>
 * </ul>
 *
 * @author Andesson Reis
 */
public record DocumentUpdateRequestDTO(
        /**
         * The new name of the document.
         */
        @JsonProperty("name")
        @NotBlank(message = "Name cannot be blank.")
        String name,

        /**
         * The new file extension (e.g., "pdf", "jpg").
         */
        @JsonProperty("fileExtension")
        @NotBlank(message = "File extension cannot be blank.")
        String fileExtension,

        /**
         * The new file storage URL.
         */
        @JsonProperty("fileUrl")
        @NotBlank(message = "File URL cannot be blank.")
        String fileUrl,

        /**
         * An optional note related to the submission or update.
         */
        @JsonProperty("submissionNote")
        String submissionNote
) {
}