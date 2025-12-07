package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO para salvar um novo perfil de um usuário
 * @author Jorge Roberto, Giovane Neves
 */
public record PerfilSalvarRequestDTO(
        @JsonProperty(value = "tipo")
        @NotBlank(message = "Tipo é obrigatório")
        String tipo,

        @JsonProperty(value = "imagemUrl")
        String imagemUrl,

        @JsonProperty("usuarioId")
        @NotNull(message = "ID do usuário é obrigatório.")
        UUID usuarioId
) {

}
