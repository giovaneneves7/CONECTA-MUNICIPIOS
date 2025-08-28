package br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID id;
    private String protocolNumber;
    private LocalDateTime createdAt;
    private LocalDateTime estimatedCompletionDate;
    private LocalDateTime updatedAt;
    private String type;
    private String note;

    private MunicipalServiceResponseDto municipalService;
}
