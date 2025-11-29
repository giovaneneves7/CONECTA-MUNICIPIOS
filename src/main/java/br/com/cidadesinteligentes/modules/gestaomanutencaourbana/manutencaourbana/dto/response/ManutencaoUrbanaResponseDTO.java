package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Viabilidade;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ManutencaoUrbanaResponseDTO {
    private Long id;
    private String name;
    private String description;

    // Devolvemos os objetos completos para o front saber o nome da rua, da categoria, etc.
    private CategoriaResponseDTO categoria;
    private EnderecoResponseDTO endereco;
    private GestorResponseDTO gestor;

    private String protocolo;
    private String imageURL;
    private Prioridade prioridade;
    private Viabilidade viabilidade;
    private LocalDateTime dataCriada;
    private LocalDateTime dataAtualizacao;
}