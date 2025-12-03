package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Viabilidade;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ManutencaoUrbanaResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("nome")
        String name, // Mapeia do getNome() ou getName() da entidade pai

        @JsonProperty("descricao")
        String description,

        @JsonProperty("categoria")
        CategoriaResponseDTO categoria,

        @JsonProperty("endereco")
        EnderecoResponseDTO endereco,

        @JsonProperty("gestor")
        GestorResponseDTO gestor,

        @JsonProperty("protocolo")
        String protocolo,

        @JsonProperty("urlImagem")
        String imageURL,

        @JsonProperty("prioridade")
        Prioridade prioridade,

        @JsonProperty("viabilidade")
        Viabilidade viabilidade,

        @JsonProperty("dataCriada")
        LocalDateTime dataCriada,

        @JsonProperty("dataAtualizacao")
        LocalDateTime dataAtualizacao
) {}