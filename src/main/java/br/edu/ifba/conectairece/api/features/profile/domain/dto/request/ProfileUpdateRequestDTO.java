package br.edu.ifba.conectairece.api.features.profile.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) for receiving profile data in API requests(put or patch).
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
public record ProfileUpdateRequestDTO (
        @JsonProperty(value = "id")
        @NotNull(message = "Id is mandatory.")
        @Positive(message = "Id must be a positive number.")
        Long id,

        @JsonProperty(value = "type")
        @NotNull(message = "Type is mandatory.")
        @NotBlank(message = "Type cannot be blank.")
        String type,

        @JsonProperty(value = "imageUrl")
        String imageUrl
) {
}
