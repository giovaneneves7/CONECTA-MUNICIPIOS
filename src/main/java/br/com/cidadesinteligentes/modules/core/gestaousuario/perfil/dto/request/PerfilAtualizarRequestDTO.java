package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO para atualização das informações de perfil
 */
public record PerfilAtualizarRequestDTO(
        @JsonProperty(value = "id")
        @NotNull(message = "Id é obrigatório.")
        UUID id,

        @JsonProperty(value = "tipo")
        @NotBlank(message = "Tipo é obrigatório")
        String tipo,

        @JsonProperty(value = "imagemUrl")
        String imagemUrl
) {
}
