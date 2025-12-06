package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para criação/atualização de Atividade (request).
 *
 * @author: Giovane Neves, Andesson Reis
 */
public record AtividadeRequestDTO(

    @JsonProperty("nome")
    @NotNull(message = "'nome' é obrigatório")
    @NotBlank(message = "'nome' não pode estar vazio")
    String nome,

    @JsonProperty("codigo")
    @NotNull(message = "'codigo' é obrigatório")
    @NotBlank(message = "'codigo' não pode estar vazio")
    String codigo,

    @JsonProperty("imageUrl")
    String imageUrl

) {}
