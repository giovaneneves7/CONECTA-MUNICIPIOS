package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.enums.DiaSemana;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.model.DisponibilidadeProfissional;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Repositório para gerenciamento da grade de horários (disponibilidade) dos profissionais.
 *
 * @author Juan Teles Dias
 */
@Repository
public interface IDisponibilidadeProfissionalRepository extends JpaRepository<DisponibilidadeProfissional, UUID> {

    /**
     * Verifica se o profissional possui disponibilidade que cubra a hora informada.
     * Utilizado pelo AgendamentoService para saber se pode marcar consulta.
     *
     * @param profissional O profissional.
     * @param dia          O dia da semana.
     * @param hora         A hora do agendamento.
     * @return true se o médico estiver atendendo nesse momento.
     */
    @Query("SELECT COUNT(d) > 0 FROM DisponibilidadeProfissional d " +
            "WHERE d.profissional = :profissional " +
            "AND d.dia = :dia " +
            "AND :hora BETWEEN d.horaInicio AND d.horaFim")
    boolean existsByProfissionalAndDiaAndHora(@Param("profissional") ProfissionalSaude profissional,
                                              @Param("dia") DiaSemana dia,
                                              @Param("hora") LocalTime hora);

    /**
     * Verifica se existe conflito/sobreposição de horários para o mesmo profissional no mesmo dia.
     * <p>
     * Lógica de Overlap: Dois intervalos (A e B) se sobrepõem se (InicioA < FimB) E (FimA > InicioB).
     * </p>
     *
     * @param profissional O profissional.
     * @param dia          O dia da semana.
     * @param inicio       Hora de início do novo intervalo.
     * @param fim          Hora de fim do novo intervalo.
     * @return true se houver conflito.
     */
    @Query("SELECT COUNT(d) > 0 FROM DisponibilidadeProfissional d " +
            "WHERE d.profissional = :profissional " +
            "AND d.dia = :dia " +
            "AND d.horaInicio < :fim " +
            "AND d.horaFim > :inicio")
    boolean existsOverlapping(@Param("profissional") ProfissionalSaude profissional,
                              @Param("dia") DiaSemana dia,
                              @Param("inicio") LocalTime inicio,
                              @Param("fim") LocalTime fim);
}