package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Viabilidade;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ManutencaoUrbanaResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String protocolo;
    private String imageURL;
    private Prioridade prioridade;
    private Viabilidade viabilidade;
    private LocalDateTime dataCriada;
    private LocalDateTime dataAtualizacao;
}