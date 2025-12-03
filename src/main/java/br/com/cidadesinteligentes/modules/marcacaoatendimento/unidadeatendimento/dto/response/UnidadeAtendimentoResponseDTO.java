package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.dto.response;

import java.util.UUID;

/**
 * DTO para retorno de dados de Unidade de Atendimento.
 *
 * @author Juan Teles Dias
 */
public record UnidadeAtendimentoResponseDTO(
        UUID id,
        String nome,
        String codigoCNES,
        String endereco,
        String telefone,
        String horarioFuncionamento,
        String tipoUnidade // Retorna o valor do discriminador
) {}