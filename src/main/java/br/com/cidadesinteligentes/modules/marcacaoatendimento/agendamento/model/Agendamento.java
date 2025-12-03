package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model.Cidadao;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.enums.StatusAgendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa um agendamento realizado.
 * * @author Juan Teles Dias
 */
@Table(name = "agendamentos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Agendamento extends PersistenceEntity {

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    // Relacionamentos:

    // N:1 com ProfissionalSaude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalSaude profissional;

    // N:1 com Servico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    // N:1 com o Cidadão/Paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id",  nullable = false)
    private Cidadao paciente;
}
