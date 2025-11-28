package br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing Citizen data in API responses.
 * Contains the government profile snapshot of a citizen.
 *
 * @author Jorge Roberto, Giovane Neves
 */
public record CitizenResponseDTO (

    @JsonProperty("govProfileSnapshot")
    String govProfileSnapshot

){}
