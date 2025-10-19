package br.edu.ifba.conectairece.api.features.flow.domain.dto.response;

import br.edu.ifba.conectairece.api.features.step.domain.dto.response.StepFullDataResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record FlowFullDataResponseDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("flowSteps")
        List<StepFullDataResponseDTO> flowSteps,
        @JsonProperty("municipalServiceId")
        Long municipalServiceId

) {
}
