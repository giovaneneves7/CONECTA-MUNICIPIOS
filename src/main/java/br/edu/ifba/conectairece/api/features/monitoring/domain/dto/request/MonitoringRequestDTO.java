package br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record MonitoringRequestDTO(

        @NotNull(message = "The id cannot be null")
        @NotBlank(message = "The id cannot be blank")
        @JsonProperty("id")
        UUID id,

        @NotNull(message = "requestId is required")
        @NotBlank(message = "requestId ")
        @JsonProperty("requestId")
        UUID requestId,

        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        @JsonProperty("name")
        String name,

        @NotNull(message = "imageUrl is required")
        @NotBlank(message = "ImageUrl cannot be blank")
        @JsonProperty("imageUrl")
        String imageUrl
) {
}
