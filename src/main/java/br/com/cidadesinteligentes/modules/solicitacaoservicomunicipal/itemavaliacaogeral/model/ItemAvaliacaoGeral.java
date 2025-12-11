package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums.StatusItemAvaliacaoGeral;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Representa um item de avaliação geral associado a uma solicitação de serviço municipal.
 *
 * <p>Esta entidade armazena informações sobre a avaliação geral realizada em uma solicitação,
 * incluindo observações, status e o nome do item avaliado.</p>
 *
 * @author Andesson Reis
 */
@Entity
@Table(name = "itens_avaliacao_geral")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ItemAvaliacaoGeral extends SimplePersistenceEntity {

    /**
     * Observação ou nota textual sobre o item avaliado.
     */
    @Column(name = "observacao")
    private String observacao;

    /**
     * O status atual do item de avaliação.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusItemAvaliacaoGeral status;

    /**
     * A solicitação à qual este item de avaliação pertence.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private Solicitacao solicitacao;

    /**
     * O nome do item avaliado.
     */
    @Column(name = "nome")
    private String nome;
}