package br.edu.ifba.conectairece.api.features.citizen.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object for receiving Citizen data in API requests.
 * Used when creating or updating citizen information.
 *
 * @author Jorge Roberto
 */
public class CitizenRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String govProfileSnapshot;
}
