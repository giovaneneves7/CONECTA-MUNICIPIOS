package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.dto.response;

import java.util.List;
import java.util.UUID;

/**
 * DTO para retorno de dados detalhados de um Profissional de Saúde.
 *
 * @author Juan Teles Dias
 */
public record ProfissionalSaudeResponseDTO(
        UUID id,
        String nome,
        String cpf,
        String email,
        String crm,
        UUID unidadeId,
        String unidadeNome, // Facilitador para o frontend
        List<String> tipos, // Lista de nomes dos tipos (ex: ["Médico", "Cardiologista"])
        List<String> servicos // Lista de nomes dos serviços (ex: ["Consulta Cardiologia"])
) {}