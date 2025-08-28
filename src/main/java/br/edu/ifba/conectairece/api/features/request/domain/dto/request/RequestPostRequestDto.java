package br.edu.ifba.conectairece.api.features.request.domain.dto.request;

import java.time.LocalDateTime;

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
    private String protocolNumber;
    private LocalDateTime estimatedCompletionDate;
    private String type;
    private String note;
    private Integer municipalServiceId;
}
