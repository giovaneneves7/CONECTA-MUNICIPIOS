package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.model.DisponibilidadeProfissional;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa um profissional de saúde.
 * * @author Juan Teles Dias
 */
@DiscriminatorValue("PROFISSIONAL_SAUDE")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ProfissionalSaude extends Perfil {

    @Column(nullable = false, unique = true) // CRM deve ser único
    private String crm;

    // 1:N com UnidadeAtendimento (1 profissional vinculado a 1 unidade)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    private UnidadeAtendimento unidadeVinculada;

    // 1:N com Agendamento (Mapeamento reverso - "mappedBy" refere-se ao atributo 'profissional' em Agendamento)
    @OneToMany(mappedBy = "profissional")
    private List<Agendamento> agendamentos;

    // 1:N com DisponibilidadeProfissional (Mapeamento reverso)
    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisponibilidadeProfissional> disponibilidades;

    // N:N com TipoProfissionalSaude
    @ManyToMany
    @JoinTable(
            name = "profissional_tipo",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_id")
    )
    private List<TipoProfissionalSaude> tiposProfissional;

    // N:N com Servico
    @ManyToMany
    @JoinTable(
            name = "profissional_servico",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicosOferecidos;
}
