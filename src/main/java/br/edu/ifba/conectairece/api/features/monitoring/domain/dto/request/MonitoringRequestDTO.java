package br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request;

import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record MonitoringRequestDTO(

        @NotNull(message = "requestId is required")
        @NotBlank(message = "requestId ")
        @JsonProperty("requestId")
        UUID requestId,

        @NotNull(message = "Code is required")
        @NotBlank(message = "Code cannot be blank")
        @JsonProperty("code")
        String code,

        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        @JsonProperty("name")
        String name,

        @NotNull(message = "imageUrl is required")
        @NotBlank(message = "ImageUrl cannot be blank")
        @JsonProperty("imageUrl")
        String imageUrl,

        @NotNull(message = "monitoringStatus is required")
        @JsonProperty("monitoringStatus")
        MonitoringStatus monitoringStatus
) {
}
