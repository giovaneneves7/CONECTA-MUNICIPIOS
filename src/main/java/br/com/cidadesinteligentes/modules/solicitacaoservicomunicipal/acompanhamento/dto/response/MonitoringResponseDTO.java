package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.MonitoringStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 *  Data Transfer Object for representing Monitoring data in API responses.
 *  Contains details about a citizen service request's monitoring status, including
 *  id, name and image url.
 *  .
 * @author Giovane Neves
 */
public record MonitoringResponseDTO(
    @JsonProperty("id")
    UUID id,

    @JsonProperty("code")
    String code,

    @JsonProperty("stepId")
    UUID stepId,

    @JsonProperty("monitoringStatus")
    MonitoringStatus monitoringStatus
) { }
