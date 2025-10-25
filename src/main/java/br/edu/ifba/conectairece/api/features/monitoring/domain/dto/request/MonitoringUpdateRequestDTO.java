package br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request;

import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record MonitoringUpdateRequestDTO(

        @NotNull(message = "'Id' is required")
        @JsonProperty("id")
        UUID id,

        @NotNull(message = "'requestId' is required")
        @NotBlank(message = "requestId ")
        @JsonProperty("requestId")
        UUID requestId,

        @NotNull(message = "'stepId' is required")
        @JsonProperty("stepId")
        UUID stepId,

        @NotNull(message = "'Code' is required")
        @NotBlank(message = "Code cannot be blank")
        @JsonProperty("code")
        String code,

        @NotNull(message = "'monitoringStatus' is required")
        @JsonProperty("monitoringStatus")
        MonitoringStatus monitoringStatus

) {}
