package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a relação entre um {@link Fluxo} e uma {@link Step},
 * indicando a ordem em que a etapa deve ocorrer dentro do fluxo.
 *
 * <p>Regras de Negócio:
 * <ul>
 *   <li>Um fluxo possui várias etapas encadeadas e ordenadas.</li>
 *   <li>Esta entidade define a posição de uma etapa dentro do fluxo.</li>
 *   <li>A ordem deve ser única dentro de cada fluxo.</li>
 * </ul>
 *
 * <p>@author Andesson Reis</p>
 */
@Entity
@Table(name = "fluxo_etapas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class EtapaFluxo extends PersistenceEntity {

    /**
     * Fluxo ao qual a etapa pertence.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fluxo_id", nullable = false)
    private Fluxo fluxo;

    /**
     * Etapa associada ao fluxo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etapa_id", nullable = false)
    private Etapa etapa;
    /**
     * Ordem de execução da etapa dentro do fluxo.
     */
    @Column(name = "ordem_etapa", nullable = false)
    private long ordemEtapa;
}
