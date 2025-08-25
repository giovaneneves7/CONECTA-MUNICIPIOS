package br.edu.ifba.conectairece.api.features.function.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for sending function data in API responses.
 * Contains basic person information without sensitive data.
 *
 * @author Jorge Roberto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FunctionResponseDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
