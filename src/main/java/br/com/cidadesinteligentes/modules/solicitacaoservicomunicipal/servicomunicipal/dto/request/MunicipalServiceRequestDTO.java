package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Representa o DTO utilizado para receber dados de um Serviço Municipal em requisições da API.
 * Este record carrega informações essenciais do serviço, tais como nome, descrição e IDs das categorias associadas.
 *
 * @author: Caio Alves, Andesson Reis
 */
public record MunicipalServiceRequestDTO (

    @JsonProperty("nome")
    @NotNull(message = "O nome é obrigatório.")
    @NotBlank(message = "O nome não pode estar em branco.")
    String nome,

    @JsonProperty("descricao")
    String descricao
) {}