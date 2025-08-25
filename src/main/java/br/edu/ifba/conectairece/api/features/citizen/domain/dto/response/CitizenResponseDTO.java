package br.edu.ifba.conectairece.api.features.citizen.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Data Transfer Object for representing Citizen data in API responses.
 * Contains the government profile snapshot of a citizen.
 *
 * @author Jorge Roberto
 */
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CitizenResponseDTO {

    @JsonProperty("govProfileSnapshot")
    private String govProfileSnapshot;

}
