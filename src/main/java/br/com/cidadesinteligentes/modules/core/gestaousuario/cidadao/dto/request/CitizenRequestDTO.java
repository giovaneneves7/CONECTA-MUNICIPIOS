package br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object for receiving Citizen data in API requests.
 * Used when creating or updating citizen information.
 *
 * @author Jorge Roberto
 */
public record CitizenRequestDTO (
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String govProfileSnapshot
) {}
