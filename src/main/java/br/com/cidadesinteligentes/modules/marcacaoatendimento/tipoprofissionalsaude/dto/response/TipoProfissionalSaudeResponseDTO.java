package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.dto.response;

import java.util.UUID;

/**
 * DTO para retorno de dados de Tipo de Profissional de Saúde.
 *
 * @author Juan Teles Dias
 */
public record TipoProfissionalSaudeResponseDTO(
        UUID id,
        String nome,
        String descricao
) {}