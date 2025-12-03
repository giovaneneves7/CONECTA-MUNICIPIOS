package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO utilizado para representar os dados de um Serviço Municipal nas respostas da API.
 * Contém o identificador do serviço, seu nome e sua descrição.
 *
 *
 * @author: Caio Alves, Giovane Neves, Andesson Reis
 */
public record MunicipalServiceResponseDTO(

    @JsonProperty("id")
    Long id,

    @JsonProperty("nome")
    String nome,

    @JsonProperty("descricao")
    String descricao

) {}
