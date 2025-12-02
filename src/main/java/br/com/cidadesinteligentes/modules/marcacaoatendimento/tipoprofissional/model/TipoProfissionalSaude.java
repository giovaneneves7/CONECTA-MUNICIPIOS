package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissional.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

/**
 * Representa um tipo de profissional de saúde.
 * * @author Juan Teles Dias
 */
@Table(name = "tipos_profissionais_saude")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class TipoProfissionalSaude extends PersistenceEntity {

    private String nome;
    private String descricao;

    // N:N com ProfissionalSaude (mappedBy reflete a @ManyToMany acima)
    @ManyToMany(mappedBy = "tiposProfissional")
    private List<ProfissionalSaude> profissionais;
}
