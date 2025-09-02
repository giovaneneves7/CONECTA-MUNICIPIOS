package br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for representing Request data in API responses.
 * Contains details about a citizen's service request, including
 * protocol number, creation and update timestamps, estimated completion date,
 * type, notes, and the associated municipal service.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponseDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("protocolNumber")
    private String protocolNumber;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("estimatedCompletionDate")
    private LocalDateTime estimatedCompletionDate;

    @JsonProperty("updateAt")
    private LocalDateTime updatedAt;

    @JsonProperty("type")
    private String type;

    @JsonProperty("note")
    private String note;

    private MunicipalServiceResponseDto municipalService;
}
