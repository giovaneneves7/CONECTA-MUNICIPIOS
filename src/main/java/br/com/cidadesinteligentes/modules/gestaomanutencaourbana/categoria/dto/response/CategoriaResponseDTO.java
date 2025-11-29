package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
}