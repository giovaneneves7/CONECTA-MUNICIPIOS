package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.AcompanhamentoStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Representa a entidade de Acompanhamento, responsável por armazenar informações
 * sobre as atualizações e evoluções de uma Solicitação realizadas pelos servidores públicos.
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "acompanhamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Acompanhamento extends PersistenceEntity {

    /**
     * A solicitação à qual este acompanhamento pertence.
     */
    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    /**
     * O código identificador único do acompanhamento (regra de negócio).
     */
    @Column(name = "codigo", nullable = false)
    private String codigo;

    /**
     * O status atual do acompanhamento.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AcompanhamentoStatus status;

    /**
     * A atividade vinculada a este acompanhamento.
     */
    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    /**
     * A data e hora em que o acompanhamento foi criado.
     */
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

}