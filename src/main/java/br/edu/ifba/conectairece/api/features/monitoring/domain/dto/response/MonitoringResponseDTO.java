package br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response;

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
    @JsonProperty("name")
    String name,
    @JsonProperty("imageUrl")
    String imageUrl
) { }
