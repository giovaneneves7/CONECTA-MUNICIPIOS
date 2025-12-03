package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.dto.request;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.enums.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO para cadastro de horário de atendimento (Grade) de um profissional.
 *
 * @author Juan Teles Dias
 */
public record DisponibilidadeProfissionalRequestDTO(

        @NotNull(message = "O dia da semana é obrigatório")
        DiaSemana dia,

        @NotNull(message = "A hora de início é obrigatória")
        LocalTime horaInicio,

        @NotNull(message = "A hora de fim é obrigatória")
        LocalTime horaFim,

        @NotNull(message = "O ID do profissional é obrigatório")
        UUID profissionalId
) {}