package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ManutencaoUrbanaRequestDTO {

    // --- Campos herdados de MunicipalService ---
    @NotBlank(message = "O nome do serviço é obrigatório")
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    // --- Campos de ManutencaoUrbana ---
    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;

    @NotBlank(message = "A URL da imagem é obrigatória")
    private String imageURL;

    @NotBlank(message = "O protocolo é obrigatório")
    private String protocolo;
}
