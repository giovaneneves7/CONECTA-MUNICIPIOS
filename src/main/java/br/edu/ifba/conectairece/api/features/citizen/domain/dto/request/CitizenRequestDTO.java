package br.edu.ifba.conectairece.api.features.citizen.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving Citizen data in API requests.
 * Used when creating or updating citizen information.
 *
 * @author Jorge Roberto
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CitizenRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String govProfileSnapshot;
}
