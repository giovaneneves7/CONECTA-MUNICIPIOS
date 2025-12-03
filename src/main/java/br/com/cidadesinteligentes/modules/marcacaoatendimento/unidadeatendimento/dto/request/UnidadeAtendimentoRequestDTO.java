package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para criação e atualização de Unidades de Atendimento.
 * Inclui o campo 'tipo' para suportar a estratégia de herança (Ex: UBS).
 *
 * @author Juan Teles Dias
 */
public record UnidadeAtendimentoRequestDTO(

        @NotBlank(message = "O nome da unidade é obrigatório")
        String nome,

        @NotBlank(message = "O código CNES é obrigatório")
        String codigoCNES,

        @NotBlank(message = "O endereço é obrigatório")
        String endereco,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        String horarioFuncionamento,

        @NotBlank(message = "O tipo da unidade é obrigatório (ex: UBS)")
        @Pattern(regexp = "UBS", message = "Tipo de unidade inválido. Tipos aceitos: UBS")
        String tipoUnidade
) {}