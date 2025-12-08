package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GestorAtualizarRequestDTO(
        @JsonProperty("id")
        @NotNull(message = "O ID é obrigatório para atualização")
        UUID id,

        @JsonProperty("imagem_url") // Adicionando snake_case como boa prática
        @NotBlank(message = "A URL da imagem é obrigatória")
        String imagemUrl
) {}