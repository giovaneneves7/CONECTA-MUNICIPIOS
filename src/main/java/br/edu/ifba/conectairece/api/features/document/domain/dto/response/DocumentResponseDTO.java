package br.edu.ifba.conectairece.api.features.document.domain.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing document data in API responses.
 * Provides metadata about a document attached to a requirement.
 *
 * Contains:
 * - Document identifier.
 * - Document name.
 * - File extension.
 * - File storage URL.
 *
 * Author: Caio Alves, Giovane Neves
 */

public record DocumentResponseDTO(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("name")
    String name,

    @JsonProperty("fileExtension")
    String fileExtension,

    @JsonProperty("fileUrl")
    String fileUrl
    
){}
