package br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Objeto de transferência de dados de Cidadão.
 * Usado para criar ou atualizar informações do cidadão.
 * 
 * @author Jorge Roberto
 */
public record CidadaoRequestDTO (
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String perfilGovSnapshot
) {}
