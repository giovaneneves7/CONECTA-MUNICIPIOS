package br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * DTO para receber os dados de uma pessoa
 */
public record PessoaRequestDTO(
    @JsonProperty("cpf")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos.")
    String cpf,

    @JsonProperty("nomeCompleto")
    @NotBlank(message = "Nome completo é obrigatório")
    String nomeCompleto,

    @JsonProperty("dataNascimento")
    @NotNull(message = "Data de Nascimento é obrigatório")
    @Past(message = "A Data de nascimento, tem que ser no passado")
    LocalDate dataNascimento,

    @JsonProperty("genero")
    @NotBlank(message = "Genero é obrigatório.")
    String genero
) {}
