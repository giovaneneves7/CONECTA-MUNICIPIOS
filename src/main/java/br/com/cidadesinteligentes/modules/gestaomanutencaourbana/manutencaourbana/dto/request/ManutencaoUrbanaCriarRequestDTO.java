package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ManutencaoUrbanaCriarRequestDTO(
        @NotBlank(message = "O nome do serviço é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,

        @NotNull(message = "O endereço é obrigatório")
        Long enderecoId,

        UUID gestorId, // Opcional

        @NotNull(message = "A prioridade é obrigatória")
        Prioridade prioridade,

        @NotBlank(message = "A URL da imagem é obrigatória")
        String urlImagem,

        @NotBlank(message = "O protocolo é obrigatório")
        String protocolo
) {}