package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 *
 * @author Juan Teles Dias
 */
public record AgendamentoResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("data")
        LocalDate data,

        @JsonProperty("hora")
        LocalTime hora,

        @JsonProperty("status")
        String status,

        @JsonProperty("profissionalId")
        UUID profissionalId,

        @JsonProperty("servicoId")
        UUID servicoId,

        @JsonProperty("pacienteId")
        UUID pacienteId

) {}