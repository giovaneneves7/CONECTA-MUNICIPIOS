package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO apenas para CRIAÇÃO
public record CategoriaCriarRequestDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao
) {}

