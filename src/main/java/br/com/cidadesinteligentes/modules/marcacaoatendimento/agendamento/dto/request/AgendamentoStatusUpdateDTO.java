package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.request;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.enums.StatusAgendamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para atualização exclusiva do status do agendamento.
 * Utilizado para marcar como REALIZADO, AUSENTE, etc.
 *
 * @author Juan Teles Dias
 */
public record AgendamentoStatusUpdateDTO(

        @NotNull(message = "O novo status é obrigatório")
        @JsonProperty("status")
        StatusAgendamento status

) {}
