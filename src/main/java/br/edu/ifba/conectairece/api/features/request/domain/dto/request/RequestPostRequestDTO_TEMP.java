package br.edu.ifba.conectairece.api.features.request.domain.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving Request data in API requests.
 * Used when creating a new request, providing protocol number,
 * estimated completion date, type, notes, and the ID of the related municipal service.
 *
 * @author Caio Alves
 */

public record RequestPostRequestDTO_TEMP (
    @JsonProperty("protocolNumber")
    String protocolNumber,

    @NotNull
    LocalDateTime estimatedCompletionDate,

    @JsonProperty("type")
    String type,

    @JsonProperty("note")
    String note,

    @JsonProperty("profile_id")
    UUID profileId,

    @NotNull
    Long municipalServiceId
) {}
