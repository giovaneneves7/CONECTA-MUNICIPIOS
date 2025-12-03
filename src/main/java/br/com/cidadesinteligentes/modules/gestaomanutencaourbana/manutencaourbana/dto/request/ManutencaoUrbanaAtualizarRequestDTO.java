package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Viabilidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ManutencaoUrbanaAtualizarRequestDTO(
        @NotNull(message = "O ID é obrigatório para atualização")
        Long id,

        @NotBlank(message = "O nome do serviço é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,

        @NotNull(message = "O endereço é obrigatório")
        Long enderecoId,

        UUID gestorId,

        @NotNull(message = "A prioridade é obrigatória")
        Prioridade prioridade,

        @NotNull(message = "A viabilidade é obrigatória")
        Viabilidade viabilidade,

        @NotBlank(message = "A URL da imagem é obrigatória")
        String urlImagem,

        @NotBlank(message = "O protocolo é obrigatório")
        String protocolo
) {}