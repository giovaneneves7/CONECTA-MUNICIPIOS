package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GestorCriarRequestDTO(
        @NotBlank(message = "A URL da imagem é obrigatória")
        String imagemUrl
) {}