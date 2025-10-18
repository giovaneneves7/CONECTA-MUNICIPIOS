package br.edu.ifba.conectairece.api.features.document.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for receiving document data in API requests.
 * Used when attaching supporting documents to a requirement.
 *
 * Contains:
 * - Document name.
 * - File extension.
 * - File storage URL.
 *
 * Validation:
 * - All fields are mandatory and cannot be blank.
 *
 * Author: Caio Alves
 */
public record DocumentRequestDTO (
    @JsonProperty("name")
    @NotBlank(message = "Name cannot be blank.")
    String name,
    @JsonProperty("fileExtension")
    @NotBlank(message = "File extension cannot be blank.")
    String fileExtension,

    @JsonProperty("fileUrl")
    @NotBlank(message = "File URL cannot be blank.")
    String fileUrl,

    @JsonProperty("submissionNote")
    String submissionNote
) {}
