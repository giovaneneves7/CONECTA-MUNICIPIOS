package br.edu.ifba.conectairece.api.features.requirementType.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for representing requirement type data in API responses.
 * Provides details about a requirement type.
 *
 * Contains:
 * - Identifier.
 * - Name.
 * - Description.
 *
 * Author: Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequirementTypeResponseDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
