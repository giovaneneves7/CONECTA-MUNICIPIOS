package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO para alterar Tipo ativo do usuário, ou seja, o Perfil que está ativo do usuário
 */
public record PerfilAlterarTipoAtivoRequestDTO(
        @JsonProperty("tipoAtivo")
        @NotBlank(message = "Tipo Ativo é obrigatório")
        String tipoAtivo,

        @JsonProperty("usuarioId")
        @NotNull(message = "ID do usuário é obrigatório.")
        UUID usuarioId
) {}
