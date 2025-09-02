package br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving MunicipalService data in API requests.
 * Used when creating or updating municipal service information.
 * Includes the service's name, description, and associated category IDs.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalServiceRequestDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotNull(message = "Name is cannot be blank.")
    private String name;

    @JsonProperty("description")
    private String description;

    @NotEmpty(message = "At least one category must be informed.")
    private List<Integer> categoryIds;
}
