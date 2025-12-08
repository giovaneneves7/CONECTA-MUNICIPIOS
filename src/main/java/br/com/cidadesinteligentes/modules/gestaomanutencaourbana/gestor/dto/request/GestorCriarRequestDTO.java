package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record GestorCriarRequestDTO(
        @JsonProperty("imagem_url") // Adicionando snake_case como boa prática
        @NotBlank(message = "A URL da imagem é obrigatória")
        String imagemUrl
) {}