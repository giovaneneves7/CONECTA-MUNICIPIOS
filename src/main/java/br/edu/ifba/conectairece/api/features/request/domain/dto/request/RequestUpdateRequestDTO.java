package br.edu.ifba.conectairece.api.features.request.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for receiving Request data in API **update** requests.
 * <p>
 * Used when updating an existing request via PUT/PATCH, providing protocol number,
 * estimated completion date, type, notes, and the ID of the related municipal service.
 * <p>
 * This DTO mirrors the {@link RequestPostRequestDTO_TEMP} to ensure
 * compatibility with the service layer and validation.
 *
 * @author Andesson Reis 
 */
public record RequestUpdateRequestDTO (
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