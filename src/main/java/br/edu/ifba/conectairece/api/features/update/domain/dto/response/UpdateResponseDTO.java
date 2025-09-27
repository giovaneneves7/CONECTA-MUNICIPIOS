package br.edu.ifba.conectairece.api.features.update.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * @author Giovane Neves
 *
 * @param timestamp
 * @param note
 */
public record UpdateResponseDTO(

        @JsonProperty("timestamp")
        LocalDateTime timestamp,

        @JsonProperty("note")
        String note

) {
}
