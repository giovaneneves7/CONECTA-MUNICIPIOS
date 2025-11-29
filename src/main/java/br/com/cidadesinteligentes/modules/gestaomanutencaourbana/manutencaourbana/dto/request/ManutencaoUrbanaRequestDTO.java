package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ManutencaoUrbanaRequestDTO {

    // Herdado de MunicipalService
    @NotBlank(message = "O nome do serviço é obrigatório")
    private String name;
    private String description;

    // Relacionamentos (IDs)
    @NotNull(message = "A categoria é obrigatória")
    private Long categoriaId;

    @NotNull(message = "O endereço é obrigatório")
    private Long enderecoId;

    private UUID gestorId; // Opcional na criação

    // Campos Próprios
    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;

    @NotBlank(message = "A URL da imagem é obrigatória")
    private String imageURL;

    @NotBlank(message = "O protocolo é obrigatório")
    private String protocolo;
}