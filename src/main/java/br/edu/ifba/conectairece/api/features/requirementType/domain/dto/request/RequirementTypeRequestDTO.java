package br.edu.ifba.conectairece.api.features.requirementType.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving requirement type data in API requests.
 * Used when creating or updating requirement type definitions.
 *
 * Contains:
 * - Name of the requirement type.
 * - Description of the requirement type.
 *
 * Author: Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequirementTypeRequestDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
