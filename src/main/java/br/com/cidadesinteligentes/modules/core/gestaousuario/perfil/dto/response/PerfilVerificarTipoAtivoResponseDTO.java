package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para verificar qual o perfil ativo de um usuário
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerfilVerificarTipoAtivoResponseDTO(
        @JsonProperty("tipoAtivo")
        String tipoAtivo,

        @JsonProperty("cargo")
        String cargo
) {}
