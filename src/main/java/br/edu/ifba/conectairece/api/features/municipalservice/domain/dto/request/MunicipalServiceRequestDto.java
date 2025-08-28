package br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving MunicipalService data in API requests.
 * Used when creating or updating municipal service information.
 * Includes the service's name, description, and associated category IDs.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalServiceRequestDto {
    private String name;
    private String description;
    private List<Integer> categoryIds;
}
