package br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Data Transfer Object for representing Request data in API responses.
 * Contains details about a citizen's service request, including
 * protocol number, creation and update timestamps, estimated completion date,
 * type, notes, and the associated municipal service.
 *
 * @author Caio Alves, Giovane Neves
 */

public record RequestResponseDTO_TEMP (
    @JsonProperty("id")
    UUID id,

    @JsonProperty("protocolNumber")
    String protocolNumber,

    @JsonProperty("createdAt")
    LocalDateTime createdAt,

    @JsonProperty("estimatedCompletionDate")
    LocalDateTime estimatedCompletionDate,

    @JsonProperty("updatedAt")
    LocalDateTime updatedAt,

    @JsonProperty("type")
    String type,

    @JsonProperty("note")
    String note,

    @JsonProperty("municipalServiceId")
    Long municipalServiceId

){}
