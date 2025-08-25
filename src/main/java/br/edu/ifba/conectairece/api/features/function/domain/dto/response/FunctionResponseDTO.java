package br.edu.ifba.conectairece.api.features.function.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for sending function data in API responses.
 *
 * @author Jorge Roberto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FunctionResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
