package br.edu.ifba.conectairece.api.features.request.domain.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving Request data in API requests.
 * Used when creating a new request, providing protocol number,
 * estimated completion date, type, notes, and the ID of the related municipal service.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostRequestDto {
    @JsonProperty("protocolNumber")
    private String protocolNumber;

    @NotNull
    private LocalDateTime estimatedCompletionDate;

    @JsonProperty("type")
    private String type;

    @JsonProperty("note")
    private String note;
    
    @NotNull
    private Integer municipalServiceId;
}
