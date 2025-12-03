package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para criação/atualização de Etapa (request).
 *
 * Regras:
 * - Campos em pt-BR para manter coerência com a entidade.
 *
 * @author: Giovane Neves, Andesson Reis
 */
public record EtapaRequestDTO(

    @JsonProperty("nome")
    @NotNull(message = "'nome' é obrigatório")
    @NotBlank(message = "'nome' não pode estar vazio")
    String nome,

    @JsonProperty("code")
    @NotNull(message = "'code' é obrigatório")
    @NotBlank(message = "'code' não pode estar vazio")
    String code,

    @JsonProperty("imageUrl")
    String imageUrl

) {}
