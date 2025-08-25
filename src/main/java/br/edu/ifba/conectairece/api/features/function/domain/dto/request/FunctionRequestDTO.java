package br.edu.ifba.conectairece.api.features.function.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for receiving function data in API requests.
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class FunctionRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @JsonProperty("description")
    private String description;
}
