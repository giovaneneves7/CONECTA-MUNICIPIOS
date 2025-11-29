package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response;

import lombok.Data;

@Data
public class EnderecoResponseDTO {
    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String latitude;
    private String longitude;
}