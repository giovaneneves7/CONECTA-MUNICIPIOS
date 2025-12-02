package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa um serviço oferecido em uma unidade de saúde específica.
 * * @author Juan Teles Dias
 */
@Table(name = "servicos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Servico extends PersistenceEntity {

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    private UnidadeAtendimento unidade;

    // Relacionamento N:N com ProfissionalSaude (Profissional oferece Múltiplos Serviços)
    @ManyToMany(mappedBy = "servicosOferecidos")
    private List<ProfissionalSaude> profissionais;

    // 1:N com Agendamento (Mapeamento reverso - permite buscar agendamentos pelo serviço)
    @OneToMany(mappedBy = "servico")
    private List<Agendamento> agendamentos;
}
