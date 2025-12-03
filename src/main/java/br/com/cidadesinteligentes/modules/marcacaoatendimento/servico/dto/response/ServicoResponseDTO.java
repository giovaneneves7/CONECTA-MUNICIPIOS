package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.dto.response;

import java.util.UUID;

/**
 * DTO para retorno de dados de Serviço.
 * @author Juan Teles Dias
 */
public record ServicoResponseDTO(
        UUID id,
        String nome,
        UUID unidadeId
) {}