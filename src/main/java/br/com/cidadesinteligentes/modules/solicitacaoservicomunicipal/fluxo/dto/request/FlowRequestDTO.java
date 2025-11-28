package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request;

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
