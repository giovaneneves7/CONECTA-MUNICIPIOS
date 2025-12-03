package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.UUID;

/**
 * DTO para criação e atualização de Profissionais de Saúde.
 * <p>
 * Agrupa dados pessoais (herdados de Profile) e dados específicos do domínio de saúde (CRM, vínculos).
 * </p>
 *
 * @author Juan Teles Dias
 */
public record ProfissionalSaudeRequestDTO(

        // --- Dados de Perfil (Profile) ---
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        // --- Dados do Profissional ---
        @NotBlank(message = "O CRM é obrigatório")
        String crm,

        @NotNull(message = "A Unidade de Atendimento é obrigatória")
        UUID unidadeId,

        @NotEmpty(message = "Pelo menos um tipo de profissional (especialidade) deve ser informado")
        List<UUID> tiposIds,

        @NotEmpty(message = "Pelo menos um serviço deve ser oferecido pelo profissional")
        List<UUID> servicosIds
) {}