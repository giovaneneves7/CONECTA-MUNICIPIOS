package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AgendamentoRequestDTO(
        @NotNull(message = "Data é obrigatória")
        LocalDate data,

        @NotNull(message = "Hora é obrigatória")
        LocalTime hora,

        @NotNull(message = "ID do profissional é obrigatório")
        UUID profissionalId,

        @NotNull(message = "ID do serviço é obrigatório")
        UUID servicoId,

        @NotNull(message = "ID do paciente é obrigatório")
        UUID pacienteId
) {}