package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.enums.StatusAgendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Repositório para a entidade Agendamento.
 *
 * @author Juan Teles Dias
 */
@Repository
public interface IAgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    /**
     * Verifica se existe agendamento para o profissional, na data e hora informadas,
     * ignorando agendamentos com um status específico (ex: CANCELADO).
     *
     * Utilizado para validar conflito de horário (Regra de Negócio).
     */
    boolean existsByProfissionalAndDataAndHoraAndStatusNot(
            ProfissionalSaude profissional,
            LocalDate data,
            LocalTime hora,
            StatusAgendamento status
    );
}