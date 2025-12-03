package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GestorAtualizarRequestDTO(
        @NotNull(message = "O ID é obrigatório para atualização")
        UUID id,

        @NotBlank(message = "A URL da imagem é obrigatória")
        String imagemUrl
) {}