package br.edu.ifba.conectairece.api.features.request.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for representing Request data in API responses.
 * Contains details about a citizen's service request, including
 * protocol number, creation and update timestamps, estimated completion date,
 * type, notes, status, applicantFullName, applicantCpf, and the associated municipal service.
 */
public record RequestResponseWithDetailsDTO(
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
        Long municipalServiceId,

        @JsonProperty("status")
        String status,

        @JsonProperty("applicantFullName")
        String applicantFullName,

        @JsonProperty("applicantCpf")
        String applicantCPF
) {
}
