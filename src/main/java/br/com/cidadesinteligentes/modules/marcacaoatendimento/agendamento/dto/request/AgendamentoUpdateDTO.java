package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Juan Teles Dias
 */
public record AgendamentoUpdateDTO(

        @NotNull(message = "A nova data é obrigatória")
        @FutureOrPresent(message = "A nova data deve ser no presente ou futuro")
        @JsonProperty("data")
        LocalDate data,

        @NotNull(message = "A nova hora é obrigatória")
        @JsonProperty("hora")
        LocalTime hora

) {}
