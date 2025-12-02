package br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Objeto de Transferência de Dados para representar dados de cidadãos em respostas de API..
 * Contém o o perfil gov de um cidadão.
 *
 * @author Jorge Roberto, Giovane Neves, Caio Alves
 */
public record CidadaoResponseDTO (

    @JsonProperty("perfilGovSnapshot")
    String perfilGovSnapshot

){}
