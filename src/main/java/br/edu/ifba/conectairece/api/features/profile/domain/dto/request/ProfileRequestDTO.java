package br.edu.ifba.conectairece.api.features.profile.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for receiving profile data in API requests.
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto, Giovane Neves
 */
public record ProfileRequestDTO (
        @JsonProperty(value = "type")
        @NotNull(message = "Type is mandatory.")
        @NotBlank(message = "Type cannot be blank.")
        String type,

        @JsonProperty(value = "imageUrl")
        String imageUrl,

        @JsonProperty("userId")
        @NotNull(message = "UserId is mandatory.")
        UUID userId
) {

}
