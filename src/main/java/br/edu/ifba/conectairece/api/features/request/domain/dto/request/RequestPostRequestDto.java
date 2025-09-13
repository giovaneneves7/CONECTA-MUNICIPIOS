package br.edu.ifba.conectairece.api.features.request.domain.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving Request data in API requests.
 * Used when creating a new request, providing protocol number,
 * estimated completion date, type, notes, and the ID of the related municipal service.
 *
 * @author Caio Alves
 */

public record RequestPostRequestDto (
    @JsonProperty("protocolNumber")
    String protocolNumber,

    @NotNull
    LocalDateTime estimatedCompletionDate,

    @JsonProperty("type")
    String type,

    @JsonProperty("note")
    String note,

    @NotNull
    Integer municipalServiceId
) {}
