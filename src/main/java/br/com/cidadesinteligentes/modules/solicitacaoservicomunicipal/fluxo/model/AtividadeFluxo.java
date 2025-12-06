package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa a associação de uma {@link Atividade} dentro de um {@link Fluxo},
 * definindo sua ordem de execução.
 *
 * Nova: AtividadeFluxo
 */
@Entity
@Table(name = "fluxo_atividades") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AtividadeFluxo extends PersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fluxo_id", nullable = false)
    private Fluxo fluxo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @Column(name = "ordem", nullable = false)
    private long ordem; 
}