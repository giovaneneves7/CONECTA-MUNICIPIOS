package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ManutencaoUrbanaCriarRequestDTO(
        @JsonProperty("nome")
        @NotBlank(message = "O nome do serviço é obrigatório")
        String nome,

        @JsonProperty("descricao")
        String descricao,

        @JsonProperty("categoria_id")
        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,

        @JsonProperty("endereco_id")
        @NotNull(message = "O endereço é obrigatório")
        Long enderecoId,

        @JsonProperty("gestor_id")
        UUID gestorId, // Opcional

        @JsonProperty("prioridade")
        @NotNull(message = "A prioridade é obrigatória")
        Prioridade prioridade,

        @JsonProperty("url_imagem")
        @NotBlank(message = "A URL da imagem é obrigatória")
        String urlImagem,

        @JsonProperty("protocolo")
        @NotBlank(message = "O protocolo é obrigatório")
        String protocolo
) {}