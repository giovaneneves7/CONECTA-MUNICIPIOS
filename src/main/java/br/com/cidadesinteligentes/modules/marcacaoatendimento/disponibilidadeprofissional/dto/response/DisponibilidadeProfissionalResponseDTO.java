package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.dto.response;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.enums.DiaSemana;

import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO para retorno de dados de disponibilidade.
 *
 * @author Juan Teles Dias
 */
public record DisponibilidadeProfissionalResponseDTO(
        UUID id,
        DiaSemana dia,
        LocalTime horaInicio,
        LocalTime horaFim,
        UUID profissionalId,
        String profissionalNome
) {}