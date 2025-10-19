package br.edu.ifba.conectairece.api.features.flow.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Giovane Neves
 */
public record FlowRequestDTO(
        @JsonProperty("name")
        String name,

        @NotNull(message = "'municipalServiceId' is required")
        @JsonProperty("municipalServiceId")
        Long municipalServiceId
) {
}
