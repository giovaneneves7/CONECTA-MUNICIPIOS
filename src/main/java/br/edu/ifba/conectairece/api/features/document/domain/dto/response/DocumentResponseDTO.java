package br.edu.ifba.conectairece.api.features.document.domain.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
 * Author: Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fileExtension")
    private String fileExtension;

    @JsonProperty("fileUrl")
    private String fileUrl;
    
}
