package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.enums.DiaSemana;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

/**
 * Representa a disponibilidade de um profissional de saúde.
 * * @author Juan Teles Dias
 */
@Table(name = "disponibilidades_profissionais")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class DisponibilidadeProfissional extends PersistenceEntity {

    @Enumerated(EnumType.STRING)
    private DiaSemana dia;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    // N:1 com ProfissionalSaude (O lado "muitos" da relação)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalSaude profissional;
}
