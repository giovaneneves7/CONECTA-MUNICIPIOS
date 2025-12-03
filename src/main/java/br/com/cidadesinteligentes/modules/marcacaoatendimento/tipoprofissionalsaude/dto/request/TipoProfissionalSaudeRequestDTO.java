package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação e atualização de Tipos de Profissional de Saúde.
 *
 * @author Juan Teles Dias
 */
public record TipoProfissionalSaudeRequestDTO(

        @NotBlank(message = "O nome do tipo é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao
) {}