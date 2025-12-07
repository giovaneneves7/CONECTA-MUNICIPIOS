package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

/**
 * DTO que retorna apenas o ID do perfil
 *
 * @author Andesson Reis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerfilApenasIdResponseDTO(UUID id) {
}
